package biz.gelicon.capital.config;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.repository.TableRepository;
import biz.gelicon.capital.utils.DatabaseUtils;
import biz.gelicon.capital.utils.RecreateDatabase;
import biz.gelicon.capital.utils.TableMetadata;
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
    }
}