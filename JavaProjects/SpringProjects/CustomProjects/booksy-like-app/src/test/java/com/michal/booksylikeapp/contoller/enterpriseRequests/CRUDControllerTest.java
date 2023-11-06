package com.michal.booksylikeapp.contoller.enterpriseRequests;

import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.EnterpriseDto;
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
class CRUDControllerTest {

    private static Long enterpriseId;

    // set params to test
    private static final String name = "Enterprise 1";
    private static final String emailToCreate = "e1@gmail.com";
    private static final String emailToUpdate = "e1@notGmail.com";
    private static final String description = "long and boring one";
    private static final String username = "e1";
    private static final String password = "password";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void createEnterpriseTest() throws Exception {

        // given
        EnterpriseDto enterpriseDto = EnterpriseDto.builder().name(name).description(description).email(emailToCreate)
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

    @Test
    @Order(2)
    void readEnterpriseTest() throws Exception {

        // given - from test1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/enterprise/enterpriseId={enterpriseId}", enterpriseId));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.name").value(name));
    }

    @Test
    @Order(3)
    void updateEnterpriseTest() throws Exception {

        // given (+from test 1)
        EnterpriseDto enterpriseDto = EnterpriseDto.builder().name(name).description(description).email(emailToUpdate)
                .username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/enterprise/enterpriseId={enterpriseId}", enterpriseId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(enterpriseDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.name").value(name));
        response.andExpect(jsonPath("$.email").value(emailToUpdate));
    }

    @Test
    @Order(4)
    void deleteEnterpriseTest() throws Exception {

        // given - from test1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/enterprise/enterpriseId={enterpriseId}", enterpriseId));

        // then
        response.andExpect(status().isNoContent());

    }
}