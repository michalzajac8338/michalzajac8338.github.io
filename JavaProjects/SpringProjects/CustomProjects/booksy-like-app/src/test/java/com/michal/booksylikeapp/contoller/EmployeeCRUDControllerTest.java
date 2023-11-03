package com.michal.booksylikeapp.contoller;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.EmployeeDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeCRUDControllerTest {

    private static Long employeeId;
    private static final String firstName = "Michal";
    private static final String lastName = "Zajac";
    private static final String emailToCreate = "michal@gmail.com";
    private static final String emailToUpdate = "michal.zajac8338@gmail.com";
    private static final String username = "michal";
    private static final String password = "michal";
    private static final Long enterpriseId = 1L;

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createEmployeeTest() throws Exception {

        // given
        EmployeeDto employeeDto = EmployeeDto.builder().enterpriseId(enterpriseId).firstName(firstName)
                .lastName(lastName).email(emailToCreate).username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                                .post("/B/enterprise/enterpriseId={enterpriseId}/employee", enterpriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(emailToCreate));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        employeeId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());

    }

    @Test
    @Order(2)
    void readEmployeeTest() throws Exception {

        // given - from test1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}", enterpriseId,employeeId));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.lastName").value(lastName));

    }

    @Test
    @Order(3)
    void updateEmployeeTest() throws Exception {

        // given (+from test 1)
        EmployeeDto updatedEmployeeDto = EmployeeDto.builder().enterpriseId(enterpriseId).firstName(firstName)
                .lastName(lastName).email(emailToUpdate).username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}", enterpriseId,employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedEmployeeDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(emailToUpdate));

    }

    @Test
    @Order(4)
    void deleteEmployeeTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/enterprise/enterpriseId={enterpriseId}/employee/employeeId={employeeId}", enterpriseId,employeeId));

        // then
        response.andExpect(status().isNoContent());
    }
}