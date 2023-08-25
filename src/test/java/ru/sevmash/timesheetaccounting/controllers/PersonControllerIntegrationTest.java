package ru.sevmash.timesheetaccounting.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sevmash.timesheetaccounting.TimesheetAccountingApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TimesheetAccountingApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PersonControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void SimpleTestForMe() throws Exception {

        mockMvc.perform(get("/api/person")).andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]['firstName']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['id']").isNotEmpty());
    }
}
