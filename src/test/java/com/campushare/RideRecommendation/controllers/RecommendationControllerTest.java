package com.campushare.RideRecommendation.controllers;

import com.campushare.RideRecommendation.dto.PostDetailDto;
import com.campushare.RideRecommendation.services.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecommendationService recommendationService;


    @Test
    void getTopPostsForUserEmptyResponse() throws Exception {
        when(recommendationService.getTopPostsForUser("userId")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/recommendations/userId/top-posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTopPostsForUserNonEmptyResponse() throws Exception {
        List<PostDetailDto> mockPosts = Arrays.asList(new PostDetailDto(), new PostDetailDto());
        when(recommendationService.getTopPostsForUser("userId")).thenReturn(mockPosts);

        mockMvc.perform(get("/recommendations/userId/top-posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(mockPosts.size()));
    }

}
