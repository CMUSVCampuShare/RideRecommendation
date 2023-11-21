package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.model.User;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.events.EventManager;
import com.campushare.RideRecommendation.repositories.UserRepository;
import com.campushare.RideRecommendation.recommendationAlgo.RecommendationAlgorithm;
import com.campushare.RideRecommendation.utils.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final RecommendationAlgorithm recommendationAlgorithm;
    private final EventManager eventManager;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository, UserRepository userRepository, UserRepository userRepository1, RecommendationAlgorithm recommendationAlgorithm, EventManager eventManager) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.recommendationAlgorithm = recommendationAlgorithm;
        this.eventManager = eventManager;

        // Subscribe to events using the EventManager
        eventManager.subscribe(EventType.USER_CREATED, this::handleUserEvent);
        eventManager.subscribe(EventType.USER_UPDATED, this::handleUserEvent);
    }


    // Example event handler methods
    private void handleUserEvent(EventData eventData) {
        // Check if the event data is relevant for user-related events
        if (isUserRelatedEvent(eventData)) {
            // Convert EventData to User model
            User user = new User();
            user.setId(eventData.getUserId());
            user.setZipcode(eventData.getZipcode());
            user.setSchedule(eventData.getSchedule());

            // Save or update user in the database
            userRepository.save(user);

            // Generate recommendations
            List<User> allUsers = userRepository.findAll();
            List<String> recommendations = recommendationAlgorithm.generateRecommendations(
                    eventData.getUserId(),
                    eventData.getSchedule(),
                    eventData.getZipcode(),
                    allUsers
            );
            saveRecommendations(eventData.getUserId(), recommendations);
        }
    }

    private void saveRecommendations(String userId, List<String> recommendedUserIds) {
        // Check if there's an existing recommendation for this user
        Recommendation existingRecommendation = recommendationRepository.findByUserId(userId);

        if (existingRecommendation != null) {
            // If an existing recommendation is found, update it
            existingRecommendation.setUsersIds(recommendedUserIds);
            recommendationRepository.save(existingRecommendation);
        } else {
            // If there is no existing recommendation, create a new one
            Recommendation newRecommendation = new Recommendation();
            newRecommendation.setUserId(userId);
            newRecommendation.setUsersIds(recommendedUserIds);
            recommendationRepository.save(newRecommendation);
        }
    }



    private boolean isUserRelatedEvent(EventData eventData) {
        // Implement logic to determine if the event data is for a user-related event
        // This might involve checking certain fields in EventData
        return eventData.getSchedule() != null && eventData.getZipcode() != null;
    }

//    public Recommendation createRecommendation(Recommendation recommendation) {
//        // Save the recommendation to the database
//        return recommendationRepository.save(recommendation);
//    }
//
//    public Recommendation getRecommendation(String userId) {
//        // Retrieve the recommendation from the database
//        return recommendationRepository.findByUserId(userId);
//    }
//
//    public Recommendation generateRecommendations(String userId) {
//        // Generate recommendations for the user with the given ID
//        // This could involve running the genetic algorithm to generate new recommendations
//        return null;
//    }
}
