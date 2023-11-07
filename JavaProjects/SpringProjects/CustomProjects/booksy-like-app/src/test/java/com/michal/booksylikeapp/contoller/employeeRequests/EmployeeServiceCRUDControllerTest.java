package com.michal.booksylikeapp.contoller.employeeRequests;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.ServiceDto;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceCRUDControllerTest {

    private static final Long employeeId = 1L;
    private static final String name = "Learn Java 8";
    private static final Double cost = 19.99;
    private static final Double costToUpdate = 14.99;
    private static final Integer durationInMinutes = 75;
    private static Long serviceId;


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void addServiceTest() throws Exception {

        // given
        ServiceDto serviceDto = ServiceDto.builder().name(name).cost(cost).durationInMin(durationInMinutes).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/employee/employeeId={employeeId}/service", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(serviceDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.cost").value(cost));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        serviceId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }

    @Test
    @Order(2)
    void readEmployeeServicesTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/employee/employeeId={employeeId}/service", employeeId));

        response.andExpect(status().isOk());
        assertTrue("Created service from test 1 is not in list of services for current employee",
                response.andReturn().getResponse().getContentAsString().contains("\"id\":"+serviceId
                        + ",\"name\":"+ "\"" + name + "\""
                        +",\"cost\":" + cost));
    }

    @Test
    @Order(3)
    void updateServiceTest() throws Exception {

        // given (+ from test 1)
        ServiceDto serviceDto = ServiceDto.builder().id(serviceId).name(name).cost(costToUpdate)
                .durationInMin(durationInMinutes).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/employee/employeeId={employeeId}/service", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(serviceDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.cost").value(costToUpdate));

    }

    @Test
    @Order(4)
    void deleteServiceTest() throws Exception {

        // given (+ from test 1)
        ServiceDto serviceDto = ServiceDto.builder().id(serviceId).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/employee/employeeId={employeeId}/service", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(serviceDto)));

        // then
        response.andExpect(status().isNoContent());
    }
}