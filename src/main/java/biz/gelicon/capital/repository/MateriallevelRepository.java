package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materiallevel;
import biz.gelicon.capital.utils.ConvertUnils;
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
public class MateriallevelRepository implements TableRepository<Materiallevel> {

    private static final Logger logger = LoggerFactory.getLogger(MateriallevelRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/500500-materiallevel.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("materiallevel created");
    }

    @Override
    public int load() {
        insert(new Materiallevel(1, 1, "Справочник материалов и услуг", null,
                ConvertUnils.getMinDate(), ConvertUnils.getMaxDate()));
        insert(new Materiallevel(2, 1, "Материалы", null,
                ConvertUnils.getMinDate(), ConvertUnils.getMaxDate()));
        insert(new Materiallevel(3, 1, "Услуги", null,
                ConvertUnils.getMinDate(), ConvertUnils.getMaxDate()));
        DatabaseUtils.setSequence("materiallevel_id_gen", 4, jdbcTemplate);
        logger.info("materiallevel loaded");
        return 0;
    }

}
