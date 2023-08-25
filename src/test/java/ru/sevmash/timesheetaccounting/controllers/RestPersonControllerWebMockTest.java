package ru.sevmash.timesheetaccounting.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RestPersonController.class)
public class RestPersonControllerWebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    List<PersonDto> persons;

    private PersonDto personDtoId1;
    private PersonDto personDtoId2;
    String personsJsonString;
    @MockBean
    private PersonConverter personConverter;

    @BeforeEach
    public void setUp() {
        personDtoId1 = new PersonDto();
        personDtoId1.setId(1L);
        personDtoId1.setFirstName("John");
        personDtoId1.setSecondName("Doe");
        personDtoId1.setPersonNumber(30);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-08-01");
            personDtoId1.setDateOfBirth(new java.sql.Date(date.getTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        personDtoId2 = new PersonDto();
        personDtoId2.setId(2L);
        personDtoId2.setFirstName("John2");
        personDtoId2.setSecondName("Doe2");
        personDtoId2.setPersonNumber(33);
        personDtoId2.setDateOfBirth(new java.sql.Date(new Date().getTime()));
        personDtoId2.setDateOfBirth(java.sql.Date.valueOf(LocalDate.parse("2023-08-01")));
        personDtoId2.setDateOfBirth(java.sql.Date.valueOf("2023-08-01")); // для себя 2 строки выше лишние

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

        String jsonPersonDto1 = gson.toJson(personDtoId1, PersonDto.class);
//        System.out.printf("%s", jsonPersonDto1);
        String jsonPersonDto2 = gson.toJson(personDtoId2, PersonDto.class);
//        System.out.printf("%s", jsonPersonDto1);

        JsonObject[] jsonArray = {(JsonObject) JsonParser.parseString(jsonPersonDto1), (JsonObject) JsonParser.parseString(jsonPersonDto2)};

        personsJsonString = gson.toJson(jsonArray);
        System.out.println(personsJsonString);

    }


    @Test
    public void testGetAllPersons() throws Exception {
        List<PersonDto> persons = Arrays.asList(personDtoId1, personDtoId2);
        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['id']", is(1)))
                .andExpect(jsonPath("$[*]['id']", hasItems(1, 2)))
                .andExpect(MockMvcResultMatchers.content().json(personsJsonString));
    }

    @Test
    public void testGetPersonById() throws Exception {
        // todo сделать инекцию
        PersonEntity personEntity1 = new PersonConverter(new ModelMapper()).toEntity(personDtoId1);
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
        when(personService.addNewPerson(any(PersonDto.class))).thenReturn(personDtoId1);
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