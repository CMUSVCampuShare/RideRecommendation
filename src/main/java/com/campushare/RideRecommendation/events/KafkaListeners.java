package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.dto.UserDetailDto;
import com.campushare.RideRecommendation.events.EventManager;
import com.campushare.RideRecommendation.model.Schedule;
import com.campushare.RideRecommendation.utils.EventType;
import com.campushare.RideRecommendation.events.data.EventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class KafkaListeners {

    private final EventManager eventManager;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaListeners(EventManager eventManager, ObjectMapper objectMapper) {
        this.eventManager = eventManager;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "create_user_topic", groupId = "recommendation-management-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenToCreateUserTopic(String message) throws JsonProcessingException {

        System.out.println("create_user: " + message);

            UserDetailDto userDetail = objectMapper.readValue(message, UserDetailDto.class);
            String userId = userDetail.getUser().getUserId();
            String zipcode = extractZipCode(userDetail.getUser().getAddress());

            Schedule schedule = new Schedule();
            schedule.setEntryTime(userDetail.getUser().getEntryTime());
            schedule.setExitTime(userDetail.getUser().getExitTime());

            EventData eventData = new EventData(userId, zipcode, schedule);
            eventManager.notify(EventType.USER_CREATED, eventData);
    }

    @KafkaListener(topics = "edit_user_topic", groupId = "recommendation-management-group",  containerFactory = "kafkaListenerContainerFactory")
    public void listenToEditUserTopic(String message) throws JsonProcessingException {

        System.out.println(message);

            UserDetailDto userDetail = objectMapper.readValue(message, UserDetailDto.class);
            String userId = userDetail.getUser().getUserId();
            String zipcode = extractZipCode(userDetail.getUser().getAddress());

            Schedule schedule = new Schedule();
            schedule.setEntryTime(userDetail.getUser().getEntryTime());
            schedule.setExitTime(userDetail.getUser().getExitTime());

            EventData eventData = new EventData(userId, zipcode, schedule);
            eventManager.notify(EventType.USER_UPDATED, eventData);
    }

    private String extractZipCode(String address) {
        if (address == null || address.isEmpty()) {
            return null;
        }
        String[] parts = address.split(",");
        if (parts.length < 2) {
            return null;
        }
        return parts[parts.length - 1].trim();
    }

}
