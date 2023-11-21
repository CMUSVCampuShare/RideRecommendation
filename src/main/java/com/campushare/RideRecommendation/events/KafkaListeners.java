package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.dto.UserDetailDto;
//import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.events.EventManager;
import com.campushare.RideRecommendation.utils.EventType;
import com.campushare.RideRecommendation.events.data.EventData;
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

    @KafkaListener(topics = "create_user_topic")
    public void listenToCreateUserTopic(String message) {
        try {
            UserDetailDto userDetail = objectMapper.readValue(message, UserDetailDto.class);
            String userId = userDetail.getId();
            String zipcode = extractZipCode(userDetail.getAddress());
            EventData eventData = new EventData(userId, zipcode, userDetail.getSchedule());
            eventManager.notify(EventType.USER_CREATED, eventData);
        } catch (IOException e) {
            System.err.println("Error processing message from create_user_topic: " + e.getMessage());
            e.printStackTrace();
            // Handle the exception as you see fit
        }
    }

    /*
    Assumes that address is formatted like: "Street Address, City, State, Zip Code"
     */
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

    @KafkaListener(topics = "edit_user_topic")
    public void listenToEditUserTopic(String message) {
        try {
            UserDetailDto userDetail = objectMapper.readValue(message, UserDetailDto.class);
            String userId = userDetail.getId();
            String zipcode = extractZipCode(userDetail.getAddress());
            EventData eventData = new EventData(userId, zipcode, userDetail.getSchedule());
            eventManager.notify(EventType.USER_UPDATED, eventData);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error processing message from edit_user_topic: " + e.getMessage());
            // Handle exceptions
        }
    }
//
//    @KafkaListener(topics = "create_post_topic")
//    public void listenToCreatePostTopic(String message) {
//        try {
//            PostDetailDto postDetail = objectMapper.readValue(message, PostDetailDto.class);
//            // Here, you can create and send an event data object to the EventManager
//            // For example, you might want to include the postId and the detail of the post
//            EventData eventData = new EventData(postDetail.getPostId(), postDetail.getDetail());
//            eventManager.notify(EventType.POST_CREATED, eventData);
//        } catch (IOException e) {
//            System.err.println("Error processing message from create_post_topic: " + e.getMessage());
//            e.printStackTrace();
//            // Properly handle the exception
//        }
//    }
//
//    @KafkaListener(topics = "edit_post_topic")
//    public void listenToEditPostTopic(String message) {
//        try {
//            PostDetailDto postDetail = objectMapper.readValue(message, PostDetailDto.class);
//            // Similar to above, but for post updates
//            EventData eventData = new EventData(postDetail.getPostId(), postDetail.getDetail());
//            eventManager.notify(EventType.POST_UPDATED, eventData);
//        } catch (IOException e) {
//            System.err.println("Error processing message from edit_post_topic: " + e.getMessage());
//            e.printStackTrace();
//            // Properly handle the exception
//        }
//    }

}
