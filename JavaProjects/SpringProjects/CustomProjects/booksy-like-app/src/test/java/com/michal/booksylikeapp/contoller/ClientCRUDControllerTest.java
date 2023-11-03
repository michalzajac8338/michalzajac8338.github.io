package com.michal.booksylikeapp.contoller;

import static com.michal.booksylikeapp.InitializeRolesTest.asJsonString;
import com.jayway.jsonpath.JsonPath;
import com.michal.booksylikeapp.dto.ClientDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientCRUDControllerTest {

    private static Long clientId;
    private static final String firstName = "Michal";
    private static final String lastName = "Zajac";
    private static final String emailToCreate = "michal@gmail.com";
    private static final String emailToUpdate = "michal.zajac8338@gmail.com";
    private static final String username = "michal";
    private static final String password = "michal";

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void createClientTest() throws Exception {

        // given
        ClientDto clientDto = ClientDto.builder().firstName(firstName).lastName(lastName).email(emailToCreate)
                .username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(clientDto)));

        // then
        response.andExpect(status().isCreated());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(emailToCreate));

        // assigning id to test the other operations on same instance
        MvcResult response1 = response.andReturn();
        clientId = Long.valueOf(JsonPath.read(response1.getResponse().getContentAsString(), "$.id").toString());

    }

    @Test
    @Order(2)
    void readClientTest() throws Exception {

        // given - from test1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/client/clientId={clientId}", clientId));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.lastName").value(lastName));

    }

    @Test
    @Order(3)
    void updateClientTest() throws Exception {

        // given (+from test 1)
        ClientDto updatedClientDto = ClientDto.builder().firstName(firstName).lastName(lastName).email(emailToUpdate)
                .username(username).password(password).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .put("/B/client/clientId={clientId}", clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedClientDto)));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.firstName").value(firstName));
        response.andExpect(jsonPath("$.email").value(emailToUpdate));

    }

    @Test
    @Order(4)
    void deleteClientTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete("/B/client/clientId={clientId}", clientId));

        // then
        response.andExpect(status().isNoContent());
    }

}