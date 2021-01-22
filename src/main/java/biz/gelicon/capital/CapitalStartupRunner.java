package biz.gelicon.capital;

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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.Table;
import java.util.Set;

/**
 * Запускается при запуске Spring и инициализирует все что надо
 */
@ConfigurationProperties
@Component
public class CapitalStartupRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CapitalStartupRunner.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RecreateDatabase recreateDatabase;

    @Value("${recreatedatabase}")
    private Boolean rcdb = false;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("CapitalStartupRunner started with option names : {}", args.getOptionNames());
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
        logger.info("Reading @Table metadata...Ok - " + TableRepository.tableMetadataMap.size());
        if (rcdb && false) { // Пересоздание базы данных
            logger.info("recreateDatabase...");
            recreateDatabase.recreate();
            recreateDatabase = CapitalApplication.getApplicationContext()
                    .getBean(RecreateDatabase.class);
            logger.info("recreateDatabase...Ok");
        }
    }

}
