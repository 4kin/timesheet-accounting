package ru.sevmash.timesheetaccounting.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sevmash.timesheetaccounting.convertor.PersonConverter;
import ru.sevmash.timesheetaccounting.domain.PersonDto;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.services.PersonService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RestPersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;
//    @Autowired
//    private PersonConverter personConverter;
    private PersonDto personDto1;
    private PersonDto personDto2;


    @BeforeEach
    public void setUp() {
        personDto1 = new PersonDto();
        personDto1.setId(1l);
        personDto1.setFirstName("John");
        personDto1.setSecondName("Doe");
        personDto1.setPersonNumber(30);
        // todo установить определенную дату и обновить строку responseString
        personDto1.setDateOfBirth(new java.sql.Date(new Date().getTime()));

        personDto2 = new PersonDto();
        personDto2.setId(2l);
        personDto2.setFirstName("John2");
        personDto2.setSecondName("Doe2");
        personDto2.setPersonNumber(33);
        // todo установить определенную дату и обновить строку responseString
        personDto2.setDateOfBirth(new java.sql.Date(new Date().getTime()));

//        String jsonPersonDto1 = new Gson().toJson(personDto1, personDto1.getClass());
//        System.out.printf("%s", jsonPersonDto1);

    }


    @Test
    public void testGetAllPersons() throws Exception {
        List<PersonDto> persons = Arrays.asList(personDto1, personDto2);
        when(personService.getAllPersons()).thenReturn(persons);
        String responseString = """
                [{"id":1,"firstName":"John","secondName":"Doe","middleName":null,"dateOfBirth":"2023-08-01","personNumber":30,"deleted":false},{"id":2,"firstName":"John2","secondName":"Doe2","middleName":null,"dateOfBirth":"2023-08-01","personNumber":33,"deleted":false}]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseString));
    }

    @Test
    public void testGetPersonById() throws Exception {
        // todo сделать инекцию
        PersonEntity personEntity1 = new PersonConverter(new ModelMapper()).toEntity(personDto1);
//        PersonEntity personEntity1 = personConverter.toEntity(personDto1);
        when(personService.getPersonById(1L)).thenReturn(personEntity1);
        String responseString = """
                {"id":1,"firstName":"John","secondName":"Doe","middleName":null,"dateOfBirth":"2023-08-01","personNumber":30,"timeSheetEntities":null,"deleted":false}
                """;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseString));
    }

    @Test
    public void testNewPerson() throws Exception {
        String jsonPerson = """
                {"firstName":"John","lastName":"Doe","age":30,"email":"john.doe@example.com"}
                """;
        when(personService.addNewPerson(any(PersonDto.class))).thenReturn(personDto1);
        String responseString = """
                {"id":1,"firstName":"John","secondName":"Doe","middleName":null,"dateOfBirth":"2023-08-01","personNumber":30,"deleted":false}
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
                        .contentType("application/json")
                        .content(jsonPerson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(responseString));
    }
}