package com.campushare.RideRecommendation.services;

public class EventData {
    // These fields are just examples, you should include what's relevant for your event
    private String userId;
    private String details; // This could be a JSON string or any other format you choose

    // Constructor
    public EventData(String userId, String details) {
        this.userId = userId;
        this.details = details;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
