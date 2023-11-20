package com.campushare.RideRecommendation.services;

import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.model.Schedule;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventData {
    private String userId;
    private String zipcode;
    private Schedule schedule;
    private String postId;
    private String postDetail;

    // Manually implemented constructor
    public EventData(String userId, String zipcode, Schedule schedule) {
        this.userId = userId;
        this.zipcode = zipcode;
        this.schedule = schedule;
    }

    public EventData(String postId, String postDetail) {
        this.postId = postId;
        this.postDetail = postDetail;
    }
}
