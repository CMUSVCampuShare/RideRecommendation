package com.campushare.RideRecommendation.controllers;

import com.campushare.RideRecommendation.model.Recommendation;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public Recommendation createRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.createRecommendation(recommendation);
    }

    public Recommendation getRecommendation(@RequestBody String userId) {
        return recommendationService.getRecommendation(userId);
    }

    @PostMapping
    public Recommendation generateRecommendations(@RequestBody String userId) {
        return recommendationService.generateRecommendations(userId);
    }

}
