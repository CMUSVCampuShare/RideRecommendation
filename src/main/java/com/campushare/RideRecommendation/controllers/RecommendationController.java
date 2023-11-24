package com.campushare.RideRecommendation.controllers;

import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}/top-posts")
    public List<PostDetailDto> getTopPosts(@PathVariable String userId) {
        return recommendationService.getTopPostsForUser(userId);
    }

}
