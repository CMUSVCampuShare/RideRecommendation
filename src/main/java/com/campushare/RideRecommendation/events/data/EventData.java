package com.campushare.RideRecommendation.events.data;

import com.campushare.RideRecommendation.model.Schedule;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventData {
    private String userId;
    private String zipcode;
    private Schedule schedule;

    public EventData(String userId, String zipcode, Schedule schedule) {
        this.userId = userId;
        this.zipcode = zipcode;
        this.schedule = schedule;
    }
}
