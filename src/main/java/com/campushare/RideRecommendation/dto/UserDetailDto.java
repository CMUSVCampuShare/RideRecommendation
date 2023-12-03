package com.campushare.RideRecommendation.dto;

import com.campushare.RideRecommendation.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    private User user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {
        private String userId;
        private String address;
        private String entryTime;
        private String exitTime;
    }
}
