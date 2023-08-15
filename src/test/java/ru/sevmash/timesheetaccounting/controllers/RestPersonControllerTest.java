package ru.sevmash.timesheetaccounting.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sevmash.timesheetaccounting.services.PersonService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestPersonController.class)
class RestPersonControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PersonService personService;

    @BeforeEach
    void setUp() {
    }


//    @GetMapping(path="/", produces = "application/json")
//    public getAllPersonsFirst getAllPersonsFirst() {
//        return employeeDao.getAllEmployees();
//    }

    @Test
    void getAllPerson() throws Exception {

        //todo доработать тест
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.test").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.test[*].emploeeId").isNotEmpty());
//        привет как твоит дела
    }

}