package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
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
        eventManager.subscribe(EventType.USER_CREATED, this::handleUserCreated);
        eventManager.subscribe(EventType.USER_UPDATED, this::handleUserUpdated);
    }

    // Example event handler methods
    private void handleUserCreated(EventData eventData) {
        // Logic to handle a new user being created
        // This could involve running the genetic algorithm to generate initial recommendations
    }

    private void handleUserUpdated(EventData eventData) {
        // Logic to handle an existing user's information being updated
        // This could involve updating the user's recommendations
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

    public void generateRecommendations(String userId) {
        // Generate recommendations for the user with the given ID
        // This could involve running the genetic algorithm to generate new recommendations
    }
}
