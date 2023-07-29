package ru.sevmash.timesheetaccounting.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestPersonController.class)
class RestPersonControllerTest {

    private final MockMvc mvc;

    RestPersonControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllPerson() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/person").accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.test").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.test[*].emploeeId").isNotEmpty());
    }

    @Test
    void getPerson() {
    }

    @Test
    void getAllDeletedPerson() {
    }

    @Test
    void deletePerson() {
    }

    @Test
    void unDeletePerson() {
    }

    @Test
    void newPerson() {
    }

    @Test
    void updatePerson() {
    }
}