package com.campushare.RideRecommendation.repositories;

import com.campushare.RideRecommendation.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    Recommendation findByUserId(String userId);
}
