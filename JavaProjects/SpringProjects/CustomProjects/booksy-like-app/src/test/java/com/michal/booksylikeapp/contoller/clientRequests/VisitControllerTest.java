package com.michal.booksylikeapp.contoller.clientRequests;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.ClientVisitDto;
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

import static com.michal.booksylikeapp.InitializeAndFillDatabaseWithExampleRecordsTest.asJsonString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VisitControllerTest {

    private static Long visitId;
    private static final Long clientId = 1L;
    private static final Long employeeId = 1L;
    private static final String startTime = "2023-01-01T13:00";
    private static final Integer durationInMin = 15;
    private static final Double cost = 19.99;
    private static final String type = "Learning Java";
    private static final String description = "Lesson 03: Hibernate";
    private static final String status = "AWAITS_CONFIRMATION";

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createVisitTest() throws Exception {

        // given
        ClientVisitDto clientVisitDto = ClientVisitDto.builder().startTime(startTime).durationInMin(durationInMin)
                .cost(cost).description(description).status(status).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client/clientId={clientId}/visit/employee/employeeId={employeeId}", clientId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientVisitDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.description").value(description));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        visitId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }

    @Test
    @Order(2)
    void readVisitsForCurrentClientTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/client/clientId={clientId}/visit", clientId));

        // then
        response.andExpect(status().isOk());
        assertTrue("Created visit from test 1 is in list of visits for current client",
                response.andReturn().getResponse().getContentAsString().contains("\"id\":"+visitId));
    }

    @Test
    @Order(3)
    void readAllSingleEmployeeTimeSlotsForDurationTest() throws Exception {

        // given + from test 1
        ClientVisitDto clientVisitDto = ClientVisitDto.builder().durationInMin(durationInMin).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/client/clientId={clientId}/visit/employee/employeeId={employeeId}", clientId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientVisitDto)));

        // then
        response.andExpect(status().isOk());
        // created visit took slot time
        assertFalse(response.andReturn().getResponse().getContentAsString().contains(startTime));

    }

    @Test
    @Order(4)
    void cancelVisitByIdTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/client/clientId={clientId}/visit/visitId={visitId}", clientId, visitId));

        // then
        response.andExpect(status().isNoContent());

    }
}