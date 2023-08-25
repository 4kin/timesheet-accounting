package ru.sevmash.timesheetaccounting.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.sevmash.timesheetaccounting.TimesheetAccountingApplication;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = TimesheetAccountingApplication.class,
        webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestPersonControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Получения всех персон")
    void getAllPerson() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/person").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1]['firstName']").exists())
                .andExpect(jsonPath("$[*]['id']").isNotEmpty());
    }

    @Test
    @DisplayName("Получение одной персоны")
    void getPerson() throws Exception {
        var id = 1;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(id)));
    }

    @DisplayName("Отображение удаленных пользователей")
    @Test
    void getAllDeletedPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/person/" + "deleted").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]['id']").exists())
                .andExpect(jsonPath("$[1]['deleted']", is(true)))
                .andExpect(jsonPath("$[*].['deleted']", hasItems(true)));
    }

    @Test
    @DisplayName("Удаление персоны")
    void deletePerson() throws Exception {

        Integer id = 1;
        // если запись помечена как удалена, то восстановим её
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/" + id + "/restore").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(id)))
                .andExpect(jsonPath("$['deleted']", is(false)));
        // удалим запись
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(id)))
                .andExpect(jsonPath("$['deleted']", is(true)));
    }

    @Test
    @DisplayName("Восстановление персоны")
    void unDeletePerson() throws Exception {
        var id = 1;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/person/" + id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(id)))
                .andExpect(jsonPath("$['deleted']", is(true)));

    }

    @Test
    @DisplayName("Создание новой персоны")
    void newPerson() throws Exception {
        // id = 111
        String newPersonString = """
                {
                    "firstName": "firstName_ybp53",
                    "secondName": "secondName_p1to1",
                    "middleName": "otchestvo_n0c4d",
                    "dateOfBirth": "2023-06-03",
                    "personNumber": 15,
                    "deleted": false
                  }
                """;
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPersonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']").exists())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(contentAsString).contains("\"firstName\":\"firstName_ybp53\"",
                "\"secondName\":\"secondName_p1to1\"",
                "\"middleName\":\"otchestvo_n0c4d\"",
                "\"dateOfBirth\":\"2023-06-03\"",
                "\"personNumber\":15",
                "\"deleted\":false");
    }


    @Test
    void updatePerson() throws Exception {
        String newPersonString = """
                {
                    "id": 1,
                    "firstName": "firstName_ybp53",
                    "secondName": "secondName_p1to1",
                    "middleName": "otchestvo_n0c4d",
                    "dateOfBirth": "2023-06-03",
                    "personNumber": 15,
                    "deleted": false
                  }
                """;
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.put("/api/person").contentType(MediaType.APPLICATION_JSON).content(newPersonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['id']", is(1)))
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(contentAsString).contains("\"id\":1", "\"firstName\":\"firstName_ybp53\"",
                "\"secondName\":\"secondName_p1to1\"",
                "\"middleName\":\"otchestvo_n0c4d\"",
                "\"dateOfBirth\":\"2023-06-03\"",
                "\"personNumber\":15",
                "\"deleted\":false");

    }
}