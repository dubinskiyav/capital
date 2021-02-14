package biz.gelicon.capital;

import biz.gelicon.capital.controllers.UnitmeasureControllerTest;
import biz.gelicon.capital.utils.RecreateDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CapitalApplicationTests {

    static Logger logger = LoggerFactory.getLogger(CapitalApplicationTests.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Value("${reloadtestdata}")
    private Boolean reloadtestdata = false;

    @Autowired
    RecreateDatabase recreateDatabase;

    @BeforeEach
    public void initTests() {
        logger.info("initTests");
        CapitalApplication.setApplicationContext(applicationContext);
        if (reloadtestdata) { // Перегрузить тестовые данные
            // Сделать прогрузку данных для теста
            // Пока для тренировки делаю перегрузку начальных данных
            // но надо грузить конкретные данные для конкретного теста
            recreateDatabase.delete();
            recreateDatabase.load();
        }
    }

    // http://localhost:8080
}
