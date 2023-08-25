package ru.sevmash.timesheetaccounting.controllers;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.sevmash.timesheetaccounting.TimesheetAccountingApplication;

@SpringBootTest(classes = TimesheetAccountingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestTimeSheetControllerIntegrationTest {

    String prefixCurl = "/api/ts";
    @LocalServerPort
    private int port;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Получение всех записей по ид")
    void getAllTimeSheetByPersonId() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get(prefixCurl + "/byPerson/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]['id']").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['id']").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(contentAsString).contains("id", "person_id", "types", "date", "hours", "fileName", "notes", "deleted");
    }

    @Test
    @DisplayName("По получению всех удалённых временных меток по ID")
    void getAllTimeSheetDeletedByPersonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(prefixCurl + "/byPerson/1/deleted"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['person_id']", Matchers.hasItems(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['deleted']", Matchers.hasItems(true)));

    }

    @Test
    @DisplayName("Получение всех записей времени")
    void getAllTimeShits() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(prefixCurl + "/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['person_id']").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['id']").isArray());
    }

    @Test
    @DisplayName("Получение времени по ID")
    void getById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(prefixCurl + "/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)));
    }

    @Test
    @DisplayName("Добавление нового времени")
    void addNewTimeByPersonId() throws Exception {
        String requestBody = """
                 {
                   "types": "ADD",
                   "date": "2023-08-25",
                   "hours": 110,
                   "fileName": "fileName_9hzo6",
                   "notes": "notes_ew7i8",
                   "deleted": false
                 }
                """;
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.post(prefixCurl + "/person/3/addTime")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['person_id']", Matchers.is(3)))
                .andReturn().getResponse().getContentAsString();

        Assertions.assertThat(contentAsString).contains("\"types\":\"ADD\"",
                "\"date\"",
                "2023-08-25",
                "\"hours\":110",
                "\"fileName\":\"fileName_9hzo6\"",
                "\"notes\":\"notes_ew7i8\"",
                "\"deleted\":false");
    }

    @Test
    @DisplayName("Удаление времени")
    void deleteTimeByPersonId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(prefixCurl + "/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)),
                        MockMvcResultMatchers.jsonPath("$['deleted']", Matchers.is(true)));
    }

    @Test
    @DisplayName("Восстановление удалённого времени")
    void restoreTime() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(prefixCurl + "/1/restore"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("$['id']", Matchers.is(1)),
                        MockMvcResultMatchers.jsonPath("$['deleted']", Matchers.is(false)));
    }
}