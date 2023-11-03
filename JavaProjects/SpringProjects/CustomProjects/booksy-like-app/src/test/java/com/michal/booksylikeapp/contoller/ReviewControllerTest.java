package com.michal.booksylikeapp.contoller;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.ReviewDto;
import com.michal.booksylikeapp.entity.Review;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.michal.booksylikeapp.InitializeRolesTest.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerTest {

    private final Long clientId = 1L;
    private final Long reviewAndVisitId = 4L;
    private final Integer rating = 5;
    private final String content = "Valuable lesson";

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createReviewTest() throws Exception {

        // given
        ReviewDto reviewDto = ReviewDto.builder().rating(rating).content(content).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client/clientId={clientId}/visit/visitId={visitId}/review", clientId, reviewAndVisitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reviewDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.rating").value(rating));
        response.andExpect(jsonPath("$.content").value(content));

    }
}