package com.campushare.RideRecommendation.events.listeners;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserBehaviorListener implements EventListener {

    private final RecommendationService recommendationService;

    @Autowired
    public UserBehaviorListener(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public void update(EventData eventData) {
        System.out.println("isUserRelatedEvent: " + isUserRelatedEvent(eventData));
        if (isUserRelatedEvent(eventData)) {
            System.out.println("Processing user event and saving recommendations...");
            recommendationService.processUserEventAndSaveRecommendations(eventData);
        }
    }

    private boolean isUserRelatedEvent(EventData eventData) {
        return eventData.getSchedule() != null && eventData.getZipcode() != null;
    }
}
