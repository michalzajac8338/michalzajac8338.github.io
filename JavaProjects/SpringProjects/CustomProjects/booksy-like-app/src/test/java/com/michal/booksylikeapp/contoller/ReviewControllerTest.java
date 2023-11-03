package com.michal.booksylikeapp.contoller;

import com.michal.booksylikeapp.dto.ReviewDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.michal.booksylikeapp.InitializeAndFillDatabaseWithExampleRecordsTest.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewControllerTest {

    private final Long clientId = 1L;
    private final Long reviewAndVisitId = 3L;
    private final Integer rating = 5;
    private final Integer updatedRating = 4;
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

    @Test
    @Order(2)
    void readReviewTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/client/clientId={clientId}/visit/visitId={visitId}/review", clientId, reviewAndVisitId));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.rating").value(rating));
        response.andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @Order(3)
    void updateReviewTest() throws Exception {

        // given
        ReviewDto reviewDto = ReviewDto.builder().rating(updatedRating).content(content).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/client/clientId={clientId}/visit/visitId={visitId}/review", clientId, reviewAndVisitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reviewDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.rating").value(updatedRating));
        response.andExpect(jsonPath("$.content").value(content));
    }

    @Test
    @Order(4)
    void deleteReviewTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/client/clientId={clientId}/visit/visitId={visitId}/review", clientId, reviewAndVisitId));

        // then
        response.andExpect(status().isNoContent());

    }

}