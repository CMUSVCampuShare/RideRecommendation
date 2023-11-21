package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.events.EventManager;
import com.campushare.RideRecommendation.utils.EventType;
import com.campushare.RideRecommendation.events.data.UserEventData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final EventManager eventManager;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, EventManager eventManager) {
        this.recommendationRepository = recommendationRepository;
        this.eventManager = eventManager;
        // Subscribe to events using the EventManager
        eventManager.subscribe(EventType.USER_CREATED, this::handleUserEvent);
        eventManager.subscribe(EventType.USER_UPDATED, this::handleUserEvent);
    }


    // Example event handler methods
    private void handleUserEvent(EventData eventData) {
        if (eventData instanceof UserEventData) {
            UserEventData userEventData = (UserEventData) eventData;
            generateRecommendations(userEventData.getUserId());
        }
    }
    

    public Recommendation createRecommendation(Recommendation recommendation) {
        // Save the recommendation to the database
        return recommendationRepository.save(recommendation);
    }

    public Recommendation getRecommendation(String userId) {
        // Retrieve the recommendation from the database
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation updateRecommendation(Recommendation recommendation) {
        // Update the recommendation in the database
        return recommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(String userId) {
        // Delete the recommendation from the database
        recommendationRepository.deleteById(userId);
    }

    public void deleteAllRecommendations() {
        // Delete all recommendations from the database
        recommendationRepository.deleteAll();
    }

    public Recommendation generateRecommendations(String userId) {
        // Generate recommendations for the user with the given ID
        // This could involve running the genetic algorithm to generate new recommendations
        return null;
    }
}
