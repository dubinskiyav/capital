package biz.gelicon.capital.controllers;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.exceptions.BadPagingException;
import biz.gelicon.capital.exceptions.PostRecordException;
import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.utils.RecreateDatabase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
@WebAppConfiguration
public class MeasureControllerTest {

    static Logger logger = LoggerFactory.getLogger(MeasureControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    RecreateDatabase recreateDatabase;

    /**
     * Для пересмоздания базы данных установить recreatedatabase=true в application.properties
     */
    @Value("${reloadtestdata}")
    private Boolean reloadtestdata = false;

    @BeforeEach
    public void initTests() {
        if (true) { return; }
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

    /**
     * Вызов формы добавления - возвращает пустой json
     *
     * @throws Exception
     */
    @Test
    public void testAdd() throws Exception {
        String jsonExpect = "{\"measureId\":null,\"measureName\":null,\"id\":null}";
        this.mockMvc.perform(get("/measure/add")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(jsonExpect)));
    }

    /**
     * Тестирование выборки данных
     *
     * @throws Exception
     */
    @Test
    void measureSelectTest() throws Exception {
        logger.info("test measure start ");
        // /Создадим json равный GridDataOption для передачи в контроллер
        List<GridDataOption.OrderBy> sort = new ArrayList<>();
        sort.add(new GridDataOption.OrderBy("measureName", 0));
        GridDataOption gridDataOption = new GridDataOption();
        gridDataOption.setPageNumber(2);
        gridDataOption.setPageSize(4);
        gridDataOption.setSort(sort);
        ObjectMapper objectMapper = new ObjectMapper();
        String gridDataOptionAsString = objectMapper.writeValueAsString(gridDataOption);
        // и обратно считаем
        GridDataOption gridDataOptio1 = objectMapper
                .readValue(gridDataOptionAsString, GridDataOption.class);

        this.mockMvc.perform(post("/measure/json")
                //.content("{\"pageSize\":10, \"pageNumber\":0, \"sort\":[{\"fieldName\":\"measureName\", \"direction\":0}]}")
                .content(gridDataOptionAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // выводить результат в консоль
                .andExpect(status().isOk()) // Статус вернет 200
                .andExpect(content().string(containsString("{\"measureId\":")));
        // Проверим пагинацию
        List<Measure> measureListExpected = Stream.of(
                new Measure(26, "Магнитная индукция"),
                new Measure(25, "Магнитный поток"),
                new Measure(17, "Мощность"),
                new Measure(20, "Освещенность")
        )
                .collect(Collectors.toList());
        MvcResult result = this.mockMvc.perform(post("/measure/json")
                .content(
                        "{\"pageSize\":4, \"pageNumber\":2, \"sort\":[{\"fieldName\":\"measureName\", \"direction\":0}]}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andReturn();
        String content = result.getResponse().getContentAsString();
        logger.info("content = " + content);
        List<Measure> measureList =
                objectMapper.readValue(content, new TypeReference<>() {
                });
        //Assert.assertArrayEquals(measureListExpected.toArray(), measureList.toArray());

        logger.info("measureSelectTest() - Ok");
    }

    /**
     * Тестирование ошитбки при выборе указали пагинацию и не укажзали сортировку
     *
     * @throws Exception
     */
    @Test
    void badPaging() throws Exception {
        assertTrue(true);
        // Укажем пагинацию и не укажем сортировку
        this.mockMvc.perform(post("/measure/json")
                .content("{\"pageSize\":4, \"pageNumber\":2}")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()) // Ошибки быть не должно
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof BadPagingException))
                .andExpect(content().string(containsString("SQL build error")))
                .andExpect(content().string(containsString("LIMIT")))
                .andExpect(content().string(containsString("OFFSET")))
        ;
        logger.info("badPaging() - Ok");
    }

    /**
     * Проверка ошибочного добавления записи
     *
     * @throws Exception
     */
    @Test
    void insertErrorTest() throws Exception {
        // Создадим measure который уже существует
        Measure measure = new Measure(null, "Вес");
        ObjectMapper objectMapper = new ObjectMapper();
        String measureAsString = objectMapper.writeValueAsString(measure);
        this.mockMvc.perform(post("/measure/post")
                //.content("{\"measureId\":1,\"measureName\":\"Вес\"}")
                .content(measureAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // выводить результат в консоль
                .andExpect(status().isOk()) // Ошибки быть не должно
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof PostRecordException))
                .andExpect(
                        content().string(containsString("SQL execute filed: INSERT INTO measure")))
        ;
        logger.info("insertErrorTest() - Ok");
    }

    /**
     * Проверка безошибочного добавления-изменения-удаления записи
     *
     * @throws Exception
     */
    @Test
    void InsertUpdateDeleteOkTest() throws Exception {
        logger.info("InsertUpdateDeleteOkTest() - Start");
        // Добавление
        // Имя - случайное, начинается с Я чтобы было в конце и пагинация не сдохла
        String nameNew = "Я Мера с номером " + String
                .valueOf(ThreadLocalRandom.current().nextInt(1000000000) + 1000000000);
        Measure measure = new Measure(null, nameNew);
        ObjectMapper objectMapper = new ObjectMapper();
        String measureAsString = objectMapper.writeValueAsString(measure);
        MvcResult result = this.mockMvc.perform(post("/measure/post")
                .content(measureAsString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Ошибки быть не должно
                .andDo(print()) // выводить результат в консоль
                .andExpect(content().string(containsString(nameNew)))
                .andReturn();
        logger.info("InsertUpdateDeleteOkTest() - Ok");
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