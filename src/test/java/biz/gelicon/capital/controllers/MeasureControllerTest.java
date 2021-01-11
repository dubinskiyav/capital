package biz.gelicon.capital.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера Единицы измерения
 */
@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest(MeasureController.class)
class MeasureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // todo требуется наполнение
    @Test
    void measure() {
    }

    @Test
    void add() throws Exception {
        this.mockMvc.perform(get("/measure/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{")));
    }

    @Test
    void upd() {
    }

    @Test
    void del() {
    }

    @Test
    void post() {
    }
}