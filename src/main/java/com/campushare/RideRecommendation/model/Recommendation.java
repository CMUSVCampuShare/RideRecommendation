package com.campushare.RideRecommendation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "recommendations")
public class Recommendation {

    @Id
    private String recommendationId;
    private String userId;
    private List<Post> posts;
    private List<User> users;
}
