package com.campushare.RideRecommendation.events;

import com.campushare.RideRecommendation.dto.UserDetailDto;
import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.utils.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;

class KafkaListenersTest {

    private KafkaListeners kafkaListeners;

    @Mock
    private EventManager eventManager;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaListeners = new KafkaListeners(eventManager, objectMapper);
    }

    @Test
    void testListenToCreateUserTopic() throws IOException {
        // Prepare a mock UserDetailDto and corresponding JSON message
        UserDetailDto userDetail = new UserDetailDto();
        userDetail.setId("123");
        userDetail.setAddress("123 Main St, Anytown, AN, 12345");

        String message = objectMapper.writeValueAsString(userDetail);

        kafkaListeners.listenToCreateUserTopic(message);

        verify(eventManager).notify(eq(EventType.USER_CREATED), any(EventData.class));
    }

    @Test
    void testListenToCreateUserTopicWithInvalidMessage() {
        String invalidMessage = "Invalid JSON";

        kafkaListeners.listenToCreateUserTopic(invalidMessage);

        verify(eventManager, never()).notify(any(), any());
    }

    @Test
    void testListenToEditUserTopic() throws IOException {
        // Prepare a mock UserDetailDto and corresponding JSON message
        UserDetailDto userDetail = new UserDetailDto();
        userDetail.setId("123");
        userDetail.setAddress("123 Main St, Anytown, AN, 12345");

        String message = objectMapper.writeValueAsString(userDetail);

        kafkaListeners.listenToEditUserTopic(message);

        verify(eventManager).notify(eq(EventType.USER_UPDATED), any(EventData.class));
    }
}
