package biz.gelicon.capital.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера Единицы измерения
 */
@SpringBootTest
@AutoConfigureMockMvc
class MeasureControllerTest {

    @Autowired
    private MockMvc mockMvc;


    // todo требуется наполнение
    @Test
    void measureTest() throws Exception {
        this.mockMvc.perform(post("/measure/json")
                //.contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print()) // выводить результат в консоль
                .andExpect(status().isOk()); // Статус вернет 200

        System.out.println("test measure");
    }

    @Test
    void addTest() throws Exception {
        this.mockMvc.perform(get("/measure/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{")));
    }

    @Test
    void updTest() {
    }

    @Test
    void delTest() {
    }

    @Test
    void postTest() {
    }
}