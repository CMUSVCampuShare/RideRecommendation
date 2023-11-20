package com.campushare.RideRecommendation.events.data;

import com.campushare.RideRecommendation.model.Schedule;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEventData implements EventData {
    private String userId;
    private String zipcode;
    private Schedule schedule;
    private String postId;
    private String postDetail;

    // Manually implemented constructor
    public UserEventData(String userId, String zipcode, Schedule schedule) {
        this.userId = userId;
        this.zipcode = zipcode;
        this.schedule = schedule;
    }
}
