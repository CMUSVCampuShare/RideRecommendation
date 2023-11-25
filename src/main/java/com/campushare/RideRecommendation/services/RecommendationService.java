package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.model.User;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import com.campushare.RideRecommendation.repositories.UserRepository;
import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.recommendationAlgo.RecommendationAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final RecommendationAlgorithm recommendationAlgorithm;
    private final RestTemplate restTemplate;
    private final String postServiceEndpoint;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository,
                                 UserRepository userRepository,
                                 RecommendationAlgorithm recommendationAlgorithm,
                                 RestTemplate restTemplate,
                                 @Value("${post.service.endpoint}") String postServiceEndpoint) {
        this.recommendationRepository = recommendationRepository;
        this.userRepository = userRepository;
        this.recommendationAlgorithm = recommendationAlgorithm;
        this.restTemplate = restTemplate;
        this.postServiceEndpoint = postServiceEndpoint;
    }

    @Transactional
    public void processUserEventAndSaveRecommendations(EventData eventData) {
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

    public List<PostDetailDto> getTopPostsForUser(String userId) {
        Recommendation recommendation = recommendationRepository.findByUserId(userId);
        if (recommendation == null || recommendation.getUsersIds().isEmpty()) {
            return Collections.emptyList();
        }
        String url = postServiceEndpoint + "/top-posts?userIds=" + String.join(",", recommendation.getUsersIds());
        PostDetailDto[] postArray = restTemplate.getForObject(url, PostDetailDto[].class);
        return postArray != null ? Arrays.asList(postArray) : Collections.emptyList();
    }

}
