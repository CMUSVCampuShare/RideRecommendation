package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.events.data.EventData;
import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.model.User;
import com.campushare.RideRecommendation.model.Schedule;
import com.campushare.RideRecommendation.repositories.RecommendationRepository;
import com.campushare.RideRecommendation.repositories.UserRepository;
import com.campushare.RideRecommendation.recommendationAlgo.RecommendationAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RecommendationServiceTest {

    @Mock
    private RecommendationRepository recommendationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecommendationAlgorithm recommendationAlgorithm;

    @Mock
    private RestTemplate restTemplate;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recommendationService = new RecommendationService(recommendationRepository, userRepository, recommendationAlgorithm, restTemplate, "http://mockpostservice");
    }

    @Test
    void testGetTopPostsForUserWhenNoRecommendation() {
        String userId = "testUserId";
        when(recommendationRepository.findByUserId(userId)).thenReturn(null);

        List<PostDetailDto> result = recommendationService.getTopPostsForUser(userId);

        assertTrue(result.isEmpty());
        verify(recommendationRepository).findByUserId(userId);
        verify(restTemplate, never()).getForObject(anyString(), eq(PostDetailDto[].class));
    }

    @Test
    void testGetTopPostsForUserWithRecommendations() {
        String userId = "testUserId";
        String recommendedUserId = "recommendedUserId";
        Recommendation recommendation = new Recommendation();
        recommendation.setUsersIds(Arrays.asList(recommendedUserId));
        when(recommendationRepository.findByUserId(userId)).thenReturn(recommendation);
        when(restTemplate.getForObject("http://mockpostservice/top-posts?userIds=recommendedUserId", PostDetailDto[].class))
                .thenReturn(new PostDetailDto[]{new PostDetailDto()});

        List<PostDetailDto> result = recommendationService.getTopPostsForUser(userId);

        assertFalse(result.isEmpty());
        verify(recommendationRepository).findByUserId(userId);
        verify(restTemplate).getForObject(anyString(), eq(PostDetailDto[].class));
    }

    @Test
    void testProcessUserEventAndSaveRecommendations() {
        EventData eventData = new EventData();
        eventData.setUserId("testUserId");
        eventData.setZipcode("testZipcode");

        Schedule testSchedule = new Schedule();
        testSchedule.setEntryTime("09:00");
        testSchedule.setExitTime("17:00");
        eventData.setSchedule(testSchedule);

        User user = new User();
        user.setId("testUserId");
        user.setZipcode("testZipcode");
        user.setSchedule(testSchedule);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(recommendationAlgorithm.generateRecommendations("testUserId", testSchedule, "testZipcode", Arrays.asList(user)))
                .thenReturn(Arrays.asList("recommendedUserId"));
        when(recommendationRepository.findByUserId("testUserId")).thenReturn(null);

        recommendationService.processUserEventAndSaveRecommendations(eventData);

        verify(userRepository).save(user);
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    void testProcessUserEventAndSaveRecommendationsWithExistingRecommendation() {
        EventData eventData = new EventData();
        eventData.setUserId("testUserId");
        eventData.setZipcode("testZipcode");

        Schedule testSchedule = new Schedule();
        testSchedule.setEntryTime("09:00");
        testSchedule.setExitTime("17:00");
        eventData.setSchedule(testSchedule);

        User user = new User();
        user.setId("testUserId");
        user.setZipcode("testZipcode");
        user.setSchedule(testSchedule);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(recommendationAlgorithm.generateRecommendations("testUserId", testSchedule, "testZipcode", Arrays.asList(user)))
                .thenReturn(Arrays.asList("recommendedUserId"));
        when(recommendationRepository.findByUserId("testUserId")).thenReturn(new Recommendation());

        recommendationService.processUserEventAndSaveRecommendations(eventData);

        verify(userRepository).save(user);
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    void testProcessUserEventAndSaveRecommendationsWithExistingRecommendationAndEmptyRecommendations() {
        EventData eventData = new EventData();
        eventData.setUserId("testUserId");
        eventData.setZipcode("testZipcode");

        Schedule testSchedule = new Schedule();
        testSchedule.setEntryTime("09:00");
        testSchedule.setExitTime("17:00");
        eventData.setSchedule(testSchedule);

        User user = new User();
        user.setId("testUserId");
        user.setZipcode("testZipcode");
        user.setSchedule(testSchedule);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(recommendationAlgorithm.generateRecommendations("testUserId", testSchedule, "testZipcode", Arrays.asList(user)))
                .thenReturn(Arrays.asList());
        when(recommendationRepository.findByUserId("testUserId")).thenReturn(new Recommendation());

        recommendationService.processUserEventAndSaveRecommendations(eventData);

        verify(userRepository).save(user);
        verify(recommendationRepository).save(any(Recommendation.class));
    }

    @Test
    void testProcessUserEventAndSaveRecommendationsWithExistingRecommendationAndNullRecommendations() {
        EventData eventData = new EventData();
        eventData.setUserId("testUserId");
        eventData.setZipcode("testZipcode");

        Schedule testSchedule = new Schedule();
        testSchedule.setEntryTime("09:00");
        testSchedule.setExitTime("17:00");
        eventData.setSchedule(testSchedule);

        User user = new User();
        user.setId("testUserId");
        user.setZipcode("testZipcode");
        user.setSchedule(testSchedule);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(recommendationAlgorithm.generateRecommendations("testUserId", testSchedule, "testZipcode", Arrays.asList(user)))
                .thenReturn(null);
        when(recommendationRepository.findByUserId("testUserId")).thenReturn(new Recommendation());

        recommendationService.processUserEventAndSaveRecommendations(eventData);

        verify(userRepository).save(user);
        verify(recommendationRepository).save(any(Recommendation.class));
    }

}
