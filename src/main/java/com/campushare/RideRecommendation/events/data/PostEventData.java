package com.campushare.RideRecommendation.events.data;

import com.campushare.RideRecommendation.utils.Status;
import com.campushare.RideRecommendation.utils.Type;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class PostEventData implements EventData{
    private String postId;
    private String userId;
    private String title;
    private String from;
    private String to;
    private String details;
    private Type type;       // Ensure that Type is an enum or class defined in your project
    private Integer noOfSeats;
    private Status status;   // Ensure that Status is an enum or class defined in your project
    private Date timestamp;

    public PostEventData(String postId, String userId, String title, String from, String to, String details, Type type, Integer noOfSeats, Status status, Date timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.from = from;
        this.to = to;
        this.details = details;
        this.type = type;
        this.noOfSeats = noOfSeats;
        this.status = status;
        this.timestamp = timestamp;
    }
}
