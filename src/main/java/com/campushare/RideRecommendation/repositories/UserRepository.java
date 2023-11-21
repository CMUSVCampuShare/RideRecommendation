package com.campushare.RideRecommendation.repositories;

import com.campushare.RideRecommendation.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    // Additional custom methods, if needed
}
