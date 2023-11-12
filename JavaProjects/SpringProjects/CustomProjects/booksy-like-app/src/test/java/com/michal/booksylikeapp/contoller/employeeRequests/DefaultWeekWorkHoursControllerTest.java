package com.michal.booksylikeapp.contoller.employeeRequests;

import com.michal.booksylikeapp.dto.DefaultWeekWorkHoursDto;
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

import java.util.HashMap;
import java.util.Map;

import static com.michal.booksylikeapp.InitializeAndFillDatabaseWithExampleRecordsTest.asJsonString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DefaultWeekWorkHoursControllerTest {
    private static final Long employeeId = 1L;
    private static Map<String, Map<String, String>> dayOfWeekWorkHours = new HashMap<>();
    private static Map<String, String> monday = new HashMap<>();
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    void setDefaultWorkHoursForWeekDaysTest() throws Exception {

        // given
        monday.put("08:00", "11:30");
        monday.put("12:00", "18:00");

        Map<String, String> tuesday = new HashMap<>();
        tuesday.put("08:00", "11:30");
        tuesday.put("12:00", "18:00");

        dayOfWeekWorkHours.put("MONDAY", monday);
        dayOfWeekWorkHours.put("TUESDAY", tuesday);

        DefaultWeekWorkHoursDto dto = DefaultWeekWorkHoursDto.builder().dayOfWeekWorkHours(dayOfWeekWorkHours).build();

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/B/employee/employeeId={employeeId}/workday/defaultWorkHours", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)));

        // then
        response.andExpect(status().isCreated());

        for(String k: monday.keySet()) {
            response.andExpect(jsonPath("$.dayOfWeekWorkHours.MONDAY."+k).value(monday.get(k)));
        }
    }

    @Test
    @Order(2)
    void readDefaultWorkHoursForWeekDaysTest() throws Exception {

        // given - from test 1
        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get("/B/employee/employeeId={employeeId}/workday/defaultWorkHours", employeeId));

        // then
        response.andExpect(status().isOk());

        for(String k: monday.keySet()) {
            response.andExpect(jsonPath("$.dayOfWeekWorkHours.MONDAY."+k).value(monday.get(k)));
        }
    }
}