package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Unitmeasurerecalc;
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
public class UnitmeasurerecalcRepository implements TableRepository<Unitmeasurerecalc> {

    private static final Logger logger = LoggerFactory.getLogger(UnitmeasurerecalcRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/500400-unitmeasurerecalc.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("unitmeasurerecalc created");
    }

    @Override
    public int load() {
        insert(new Unitmeasurerecalc(11, 1, 11, 100.0)); // Метр в сантиметры
        insert(new Unitmeasurerecalc(12, 1, 12, 0.001)); // Метр в киллометры
        insert(new Unitmeasurerecalc(13, 1, 13, 1000.0)); // Метр в Миллиметры
        insert(new Unitmeasurerecalc(14, 14, 1, 1482.0)); // Миля в метры
        insert(new Unitmeasurerecalc(15, 15, 1, 0.9144)); // Ярд в метры
        insert(new Unitmeasurerecalc(16, 16, 1, 0.3048)); // Фут в метры
        insert(new Unitmeasurerecalc(17, 17, 11, 2.54)); // Дюйм в сантиметры
        insert(new Unitmeasurerecalc(21, 2, 18, 1000.0)); // Килограмм в Граммы
        insert(new Unitmeasurerecalc(22, 19, 2, 1000.0)); // Тонна в килограммы
        insert(new Unitmeasurerecalc(23, 20, 2, 100.0)); // Центнер в килограммы
        insert(new Unitmeasurerecalc(24, 21, 18, 0.2)); // Карат в граммы
        insert(new Unitmeasurerecalc(25, 22, 2, 0.45359237)); // Фунт в килограммы
        insert(new Unitmeasurerecalc(26, 23, 18, 28.3495)); // Унция в граммы
        insert(new Unitmeasurerecalc(30, 24, 25, 4.0)); // Год в кварталы
        insert(new Unitmeasurerecalc(31, 24, 26, 12.0)); // Год в месяцы
        insert(new Unitmeasurerecalc(32, 26, 27, 3.0)); // Месяц в декады
        insert(new Unitmeasurerecalc(33, 28, 29, 7.0)); // Неделя в сутки
        insert(new Unitmeasurerecalc(34, 29, 30, 24.0)); // Сутки в часы
        insert(new Unitmeasurerecalc(35, 30, 31, 60.0)); // Час в минуты
        insert(new Unitmeasurerecalc(36, 31, 3, 60.0)); // Минута в секунды
        insert(new Unitmeasurerecalc(37, 3, 32, 1000.0)); // Секунда в милисекунды
        DatabaseUtils.setSequence("unitmeasurerecalc_id_gen", 41, jdbcTemplate);
        logger.info("unitmeasurerecalc loaded");
        return 0;
    }

}
