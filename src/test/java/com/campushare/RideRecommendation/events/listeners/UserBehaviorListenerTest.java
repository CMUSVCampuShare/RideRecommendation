package com.campushare.RideRecommendation.events.listeners;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.model.Schedule;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserBehaviorListenerTest {

    @Mock
    private RecommendationService recommendationService;

    private UserBehaviorListener userBehaviorListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userBehaviorListener = new UserBehaviorListener(recommendationService);
    }

    @Test
    void whenUpdateCalledWithValidEventData_thenProcessUserEvent() {
        Schedule schedule = new Schedule();
        schedule.setEntryTime("09:00");
        schedule.setExitTime("17:00");

        EventData eventData = new EventData("userId", "zipcode", schedule);

        userBehaviorListener.update(eventData);

        verify(recommendationService).processUserEventAndSaveRecommendations(eventData);
    }

    @Test
    void whenUpdateCalledWithInvalidEventData_thenDoNotProcessUserEvent() {
        EventData eventData = new EventData("userId", null, null);

        userBehaviorListener.update(eventData);

        verify(recommendationService, never()).processUserEventAndSaveRecommendations(eventData);
    }

    @Test
    void whenUpdateCalledWithPartialEventData_thenDoNotProcessUserEvent() {
        EventData eventData = new EventData("userId", "zipcode", null);

        userBehaviorListener.update(eventData);

        verify(recommendationService, never()).processUserEventAndSaveRecommendations(eventData);
    }
}
