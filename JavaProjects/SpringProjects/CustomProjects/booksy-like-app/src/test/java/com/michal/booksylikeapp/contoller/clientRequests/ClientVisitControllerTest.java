package com.michal.booksylikeapp.contoller.clientRequests;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.VisitDto;
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
class ClientVisitControllerTest {
    private static final Long serviceId = 1L;
    private static final Long clientId = 1L;
    private static final Long employeeId = 1L;
    private static final String startTime = "2023-01-01T14:00";
    private static final String clientMessage = "Lesson 03: Hibernate";
    private static final String status = "AWAITS_CONFIRMATION";
    private static Long visitId;

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createVisitTest() throws Exception {

        // given
        VisitDto visitDto = VisitDto.builder().startTime(startTime).serviceId(serviceId)
                .clientMessage(clientMessage).status(status).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client/clientId={clientId}/visit/employee/employeeId={employeeId}", clientId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(visitDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.clientMessage").value(clientMessage));

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
        VisitDto visitDto = VisitDto.builder().serviceId(serviceId).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/client/clientId={clientId}/visit/employee/employeeId={employeeId}", clientId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(visitDto)));

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