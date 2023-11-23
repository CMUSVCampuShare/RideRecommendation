package com.campushare.RideRecommendation.events.listeners;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.campushare.RideRecommendation.model.Recommendation;
//import com.campushare.RideRecommendation.model.User;
//import com.campushare.RideRecommendation.repositories.UserRepository;
//import com.campushare.RideRecommendation.repositories.RecommendationRepository;
//import com.campushare.RideRecommendation.recommendationAlgo.RecommendationAlgorithm;
//import java.util.List;

@Component
public class UserBehaviorListener implements EventListener {

    private final RecommendationService recommendationService;

    @Autowired
    public UserBehaviorListener(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Override
    public void update(EventData eventData) {
        if (isUserRelatedEvent(eventData)) {
            recommendationService.processUserEventAndSaveRecommendations(eventData);
        }
    }

    private boolean isUserRelatedEvent(EventData eventData) {
        return eventData.getSchedule() != null && eventData.getZipcode() != null;
    }
}
