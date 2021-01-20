package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;
import biz.gelicon.capital.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

@Repository
public class MeasureunitRepository implements TableRepository<Measureunit> {

    private static final Logger logger = LoggerFactory.getLogger(MeasureunitRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create(){
        Resource resource = new ClassPathResource("sql/500300-measureunit.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("Measureunit created");
    }

    @Override
    public int load() {
        logger.info("Measureunit loaded");
        return 0;
    }

}
