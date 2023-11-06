package com.michal.booksylikeapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.*;
import com.michal.booksylikeapp.repository.RoleRepository;
import com.michal.booksylikeapp.service.RoleService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InitializeAndFillDatabaseWithExampleRecordsTest {

    // Run test to fill database with examples
    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @Test
    @Order(1)
    void initializeRoles(){
        roleService.initializeRoles();

        assertEquals(roleRepository.findAll().size(),3);
    }

    // some test to fill up the database with data
    private static Long enterpriseId;
    private static Long employeeId;
    private static Long workdayId;
    private static Long clientId;
    private static Long visitId;

    // choosing data for workdays & visits
    private static final String workdayDate = "2023-01-01";
    private static final String workStartTime = "2023-01-01T10:00";
    private static final String workEndTime = "2023-01-01T18:00";
    private static final String visitStartTime = "2023-01-01T10:00";
    private static final Integer durationInMin = 75;

    @Autowired
    MockMvc mockMvc;

    // Create enterprise
    @Test
    @Order(2)
    void createEnterpriseTest() throws Exception {

        // given
        String name = "Enterprise 1";
        String email = "e1@gmail.com";
        String description = "long and boring one";
        String username = "e1";
        String password = "password";

        EnterpriseDto enterpriseDto = EnterpriseDto.builder().name(name).description(description).email(email)
                .username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/B/enterprise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enterpriseDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.name").value(name));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        enterpriseId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }

    // Create employee
    @Test
    @Order(3)
    void createEmployeeTest() throws Exception {

        // given
        String firstName = "Michal";
        String lastName = "Zajac";
        String email = "michal@gmail.com";
        String username = "michal";
        String password = "michal";

        EmployeeDto employeeDto = EmployeeDto.builder().enterpriseId(enterpriseId).firstName(firstName)
                .lastName(lastName).email(email).username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/enterprise/enterpriseId={enterpriseId}/employee", enterpriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(email));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        employeeId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());

    }

    // Create workday of the employee
    @Test
    @Order(4)
    void createWorkdayTest() throws Exception {

        // given
        WorkdayDto workdayDto = WorkdayDto.builder().date(workdayDate).workStartTime(workStartTime).workEndTime(workEndTime)
                .build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/employee/employeeId={employeeId}/workday", enterpriseId, employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(workdayDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.date").value(workdayDate));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        workdayId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }

    // Create client
    @Test
    @Order(5)
    void createClientTest() throws Exception {

        // given
        String firstName = "Milosz";
        String lastName = "Zajac";
        String email = "milosz@gmail.com";
        String username = "milosz";
        String password = "milosz";

        ClientDto clientDto = ClientDto.builder().firstName(firstName).lastName(lastName).email(email)
                .username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(email));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        clientId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());
    }

    // Create visit
    @Test
    @Order(6)
    void createVisitTest() throws Exception {

        // given
        Double cost = 19.99;
        String description = "Lesson 03: Hibernate";
        String status = "AWAITS_CONFIRMATION";

        ClientVisitDto clientVisitDto = ClientVisitDto.builder().startTime(visitStartTime).durationInMin(durationInMin)
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

    // Create review
    @Test
    @Order(7)
    void createReviewTest() throws Exception {

        // given
        Integer rating = 5;
        String content = "Valuable lesson";

        ReviewDto reviewDto = ReviewDto.builder().rating(rating).content(content).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client/clientId={clientId}/visit/visitId={visitId}/review", clientId, visitId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(reviewDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.rating").value(rating));
        response.andExpect(jsonPath("$.content").value(content));

    }

    // public method for tests
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
