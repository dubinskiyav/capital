package biz.gelicon.capital.config;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.controllers.UnitmeasureController;
import biz.gelicon.capital.repository.TableRepository;
import biz.gelicon.capital.utils.DatabaseUtils;
import biz.gelicon.capital.utils.GridDataOption;
import biz.gelicon.capital.utils.RecreateDatabase;
import biz.gelicon.capital.utils.TableMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Запускается при запуске Spring и инициализирует все что надо
 */
@Component
public class InitApp implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    RecreateDatabase recreateDatabase;

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Для пересмоздания базы данных установить
     * recreatedatabase=true
     * в application.properties
     */
    @Value("${recreatedatabase}")
    private Boolean recreatedatabase = false;

    private static final Logger logger = LoggerFactory.getLogger(InitApp.class);

    public void run(ApplicationArguments args) {
        logger.info("InitApp running...");
        CapitalApplication.setApplicationContext(applicationContext);
        // Установим тип СУБД
        DatabaseUtils.setDbType(jdbcTemplate);
        // Считаем все аннотации @Table
        logger.info("Reading @Table metadata...");
        Reflections reflections = new Reflections("biz.gelicon.capital");
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(Table.class);
        set.forEach(s ->
                TableMetadata.getTableMetadataFromMap(
                        null,
                        TableRepository.tableMetadataMap,
                        s
                )
        );
        if (recreatedatabase) { // Пересоздание базы данных
            logger.info("recreateDatabase...");
            recreateDatabase.recreate();
            recreateDatabase = CapitalApplication.getApplicationContext()
                    .getBean(RecreateDatabase.class);
            logger.info("recreateDatabase...Ok");
        }
        logger.info("InitApp running...Ok");
        test1();
    }

    @Autowired
    UnitmeasureController unitmeasureController;
    /**
     * Для легких тестов во время разработки
     */
    private void test1() {
        if (true) {return;}
        logger.info("test1...");

        // /Создадим json равный GridDataOption для передачи в контроллер
        List<GridDataOption.OrderBy> sort = new ArrayList<>();
        sort.add(new GridDataOption.OrderBy("name", 0));
        sort.add(new GridDataOption.OrderBy("measureName", 1));
        GridDataOption gridDataOption = new GridDataOption();
        gridDataOption.setPageNumber(2);
        gridDataOption.setPageSize(4);
        gridDataOption.setSort(sort);
        unitmeasureController.unitmeasureDTO(gridDataOption).forEach(p -> System.out.println(p.toString()));
        logger.info("test1...Ok");
    }
}