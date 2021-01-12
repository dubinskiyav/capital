package biz.gelicon.capital.controllers;

import biz.gelicon.capital.CapitalApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест контроллера Единицы измерения
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MeasureControllerTest {

    static Logger logger = LoggerFactory.getLogger(MeasureControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void initTests() {
        logger.info("initTests");
        CapitalApplication.setApplicationContext(applicationContext);

    }


    // todo требуется наполнение
    @Test
    void measureTest() throws Exception {
        logger.info("test measure start ");
        this.mockMvc.perform(post("/measure/json")
                .content("{\"pageSize\":10, \"pageNumber\":0, \"sort\":[]}")
                //.content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // выводить результат в консоль
                .andExpect(status().isOk()) // Статус вернет 200
                .andExpect(content().string(containsString("{\"id\":")));
        logger.info("test measure finish");
    }

    @Test
    void addTest() throws Exception {
        if (true) {return;}
        this.mockMvc.perform(get("/measure/add"))
                .andDo(print())
                .andExpect(status().isOk())
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