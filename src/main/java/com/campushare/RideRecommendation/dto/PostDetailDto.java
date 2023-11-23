package com.campushare.RideRecommendation.dto;

import com.campushare.RideRecommendation.utils.Type;
import com.campushare.RideRecommendation.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {
    private String postId;
    private String userId;
    private String title;
    private String from;
    private String to;
    private String details;
    private Type type;
    private Integer noOfSeats;
    private Status status;
    private Date timestamp;

}