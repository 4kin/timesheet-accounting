package ru.sevmash.timesheetaccounting.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.services.PersonService;

import java.util.Date;


@WebMvcTest(RestPersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private PersonDto personDto1;
    private PersonDto personDto2;

    @BeforeEach
    public void setUp() {
        personDto1 = new PersonDto();
        personDto1.setId(1l);
        personDto1.setFirstName("John");
        personDto1.setSecondName("Doe");
        personDto1.setPersonNumber(30);
        personDto1.setDateOfBirth(new java.sql.Date(new Date().getTime()));

        personDto2 = new PersonDto();
        personDto2.setId(2l);
        personDto2.setFirstName("John2");
        personDto2.setSecondName("Doe2");
        personDto2.setPersonNumber(33);
        personDto2.setDateOfBirth(new java.sql.Date(new Date().getTime()));


    }

    @Test
    public void testGetAllPersons() throws Exception {
//        List<PersonDto> persons = Arrays.asList(personDto1, personDto2);
//        when(personService.getAllPersons()).thenReturn(persons);
//        mockMvc.perform(MockMvcRequestBuilders.get("/persons"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("[{id:1,firstName:\"John\",lastName:\"Doe\",age:30,email:\"john.doe@example.com\"},{id:2,firstName:\"Jane\",lastName:\"Doe\",age:25,email:\"jane.doe@example.com\"}]"));
    }

    @Test
    public void testGetPersonById() throws Exception {
//        when(personService.getPersonById(1L)).thenReturn(personDto1);
//        mockMvc.perform(MockMvcRequestBuilders.get("/person/1"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("{id:1,firstName:\"John\",lastName:\"Doe\",age:30,email:\"john.doe@example.com\"}"));
    }

    @Test
    public void testCreatePerson() throws Exception {
//        String jsonPerson = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"age\":30,\"email\":\"john.doe@example.com\"}";
//        when(personService.createPerson(any(PersonDto.class))).thenReturn(personDto1);
//        mockMvc.perform(MockMvcRequestBuilders.post("/person")
//                        .contentType("application/json")
//                        .content(jsonPerson))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json("{id:1,firstName:\"John\",lastName:\"Doe\",age:30,email:\"john.doe@example.com\"}"));
    }
}