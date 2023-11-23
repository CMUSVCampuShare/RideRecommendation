package com.campushare.RideRecommendation.events.listeners;

import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.model.User;
import com.campushare.RideRecommendation.repositories.UserRepository;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import com.campushare.RideRecommendation.recommendationAlgo.RecommendationAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBehaviorListener implements EventListener {

    private final UserRepository userRepository;
    private final RecommendationRepository recommendationRepository;
    private final RecommendationAlgorithm recommendationAlgorithm;

    @Autowired
    public UserBehaviorListener(UserRepository userRepository,
                                RecommendationRepository recommendationRepository,
                                RecommendationAlgorithm recommendationAlgorithm) {
        this.userRepository = userRepository;
        this.recommendationRepository = recommendationRepository;
        this.recommendationAlgorithm = recommendationAlgorithm;
    }

    @Override
    public void update(EventData eventData) {
        if (isUserRelatedEvent(eventData)) {
            processUserEvent(eventData);
        }
    }

    private boolean isUserRelatedEvent(EventData eventData) {
        return eventData.getSchedule() != null && eventData.getZipcode() != null;
    }

    private void processUserEvent(EventData eventData) {
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

        // Save the recommendations
        saveRecommendations(eventData.getUserId(), recommendations);
    }

    private void saveRecommendations(String userId, List<String> recommendedUserIds) {
        Recommendation existingRecommendation = recommendationRepository.findByUserId(userId);

        if (existingRecommendation != null) {
            existingRecommendation.setUsersIds(recommendedUserIds);
            recommendationRepository.save(existingRecommendation);
        } else {
            Recommendation newRecommendation = new Recommendation();
            newRecommendation.setUserId(userId);
            newRecommendation.setUsersIds(recommendedUserIds);
            recommendationRepository.save(newRecommendation);
        }
    }
}
