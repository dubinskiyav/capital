package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materialunitmeasure;
import biz.gelicon.capital.model.Service;
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
public class MaterialunitmeasureRepository implements TableRepository<Materialunitmeasure>{

    private static final Logger logger = LoggerFactory.getLogger(MaterialunitmeasureRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/500800-materialunitmeasure.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("material created");
    }

    @Override
    public int load() {
        insert(new Materialunitmeasure(1,1,35)); // Кока-Кола - Штук
        insert(new Materialunitmeasure(2,1,36)); // Кока-Кола - Упаковка
        insert(new Materialunitmeasure(3,2,2)); // Бананы - Килограмм
        insert(new Materialunitmeasure(4,3,50)); // Уборка помещения - Метр квадлратный
        insert(new Materialunitmeasure(5,4,51)); // Доставка товара до квартиры - Одна услуга
        DatabaseUtils.setSequence("materiallevel_id_gen", 4, jdbcTemplate);
        logger.info("materialunitmeasure loaded");
        return 0;
    }

}
