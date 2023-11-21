package com.campushare.RideRecommendation.recommendationAlgo;


import com.campushare.RideRecommendation.model.Schedule;
import com.campushare.RideRecommendation.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationAlgorithm  {

    public List<String> generateRecommendations(String currentUserId, Schedule currentUserSchedule, String currentUserZipcode, List<User> allUsers) {
        List<String> recommendedUserIds = new ArrayList<>();
        for (User user : allUsers) {
            if (!user.getId().equals(currentUserId) &&
                    user.getZipcode().equals(currentUserZipcode) &&
                    schedulesMatch(user.getSchedule(), currentUserSchedule)) {
                recommendedUserIds.add(user.getId());
            }
        }
        return recommendedUserIds;
    }

    private boolean schedulesMatch(Schedule schedule1, Schedule schedule2) {
        // Simple logic for matching schedules
        return schedule1.equals(schedule2);
    }
}
