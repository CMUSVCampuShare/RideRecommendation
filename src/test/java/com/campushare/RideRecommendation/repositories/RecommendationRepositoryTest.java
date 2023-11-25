package com.campushare.RideRecommendation.repositories;

import com.campushare.RideRecommendation.model.Recommendation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class RecommendationRepositoryTest {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection(Recommendation.class);
    }


    @AfterEach
    void cleanUp() {
        mongoTemplate.dropCollection(Recommendation.class);
    }


    @Test
    void testSaveRecommendation() {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId("testUser");
        recommendation.setUsersIds(Arrays.asList("user1", "user2"));

        Recommendation savedRecommendation = recommendationRepository.save(recommendation);

        assertNotNull(savedRecommendation.getRecommendationId()); // ID should be set after saving
        assertEquals("testUser", savedRecommendation.getUserId());
        assertEquals(2, savedRecommendation.getUsersIds().size());
    }

    @Test
    void testFindByUserId() {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId("testUser");
        recommendation.setUsersIds(Arrays.asList("user1", "user2"));
        recommendationRepository.save(recommendation);

        Recommendation foundRecommendation = recommendationRepository.findByUserId("testUser");

        assertNotNull(foundRecommendation);
        assertEquals("testUser", foundRecommendation.getUserId());
        assertTrue(foundRecommendation.getUsersIds().contains("user1"));
        assertTrue(foundRecommendation.getUsersIds().contains("user2"));
    }

    @Test
    void testFindByUserIdWhenNotFound() {
        Recommendation recommendation = new Recommendation();
        recommendation.setUserId("differentUser");
        recommendation.setUsersIds(Arrays.asList("user1", "user2"));
        recommendationRepository.save(recommendation);

        Recommendation foundRecommendation = recommendationRepository.findByUserId("testUser");

        assertNull(foundRecommendation);
    }

}
