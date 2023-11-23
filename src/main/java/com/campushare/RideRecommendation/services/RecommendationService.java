package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        // Create a new recommendation
        return recommendationRepository.save(recommendation);
    }

    public Recommendation getRecommendation(String userId) {
        // Retrieve a recommendation by user ID
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation updateRecommendation(Recommendation recommendation) {
        // Update an existing recommendation
        return recommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(String userId) {
        // Delete a recommendation by user ID
        recommendationRepository.deleteById(userId);
    }

    // Additional methods as needed for handling direct interactions with recommendations
}
