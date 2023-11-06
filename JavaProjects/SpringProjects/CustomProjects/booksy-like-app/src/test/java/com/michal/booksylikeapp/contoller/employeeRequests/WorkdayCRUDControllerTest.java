package com.michal.booksylikeapp.contoller.employeeRequests;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.WorkdayDto;
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
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WorkdayCRUDControllerTest {

    private static final Long enterpriseId = 1L;
    private static final Long employeeId = 1L;
    private static Long workdayId;
    private static final String date = "2023-02-11";
    private static final String workStartTime = "2023-02-11T10:00";
    private static final String workEndTime = "2023-02-11T16:00";
    private static final String workEndTimeUpdated = "2023-02-11T18:00";


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void createWorkdayTest() throws Exception {

        // given
        WorkdayDto workdayDto = WorkdayDto.builder().date(date).workStartTime(workStartTime).workEndTime(workEndTime)
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}/workday", enterpriseId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workdayDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.date").value(date));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        workdayId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }


    @Test
    @Order(2)
    void readAllSingleEmployeeWorkdaysForDurationTest() throws Exception {

        // given (from test 1)
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/employee/employeeId={employeeId}/workday", enterpriseId, employeeId));

        // then
        response.andExpect(status().isOk());
        assertTrue("Created workday from test 1 is in list of workdays for current employee",
                response.andReturn().getResponse().getContentAsString().contains("\"id\":"+workdayId
                        + ",\"employeeId\":"+employeeId
                        +",\"date\":\"" + date+"\""));
    }

    @Test
    @Order(3)
    void updateWorkdayTest() throws Exception {

        // given (+from test 1)
        WorkdayDto workdayDto = WorkdayDto.builder().date(date).workStartTime(workStartTime).workEndTime(workEndTimeUpdated)
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/employee/employeeId={employeeId}/workday", enterpriseId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workdayDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.workEndTime").value(workEndTimeUpdated.replace("T", " ")));
    }

    @Test
    @Order(4)
    void deleteWorkdayTest() throws Exception {

        // given - from test 1
        WorkdayDto workdayDto = WorkdayDto.builder().date(date).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete
                ("/B/employee/employeeId={employeeId}/workday", enterpriseId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workdayDto)));

        // then
        response.andExpect(status().isNoContent());
    }
}