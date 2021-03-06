package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;
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
public class MeasureRepository implements TableRepository<Measure> {

    private static final Logger logger = LoggerFactory.getLogger(MeasureRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create() {
        Resource resource = new ClassPathResource("sql/500100-measure.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("measure created");
        if (true) {return;}

        String[] sqlStatements = {""
                + "CREATE TABLE measure (\n"
                + "    id INTEGER NOT NULL,\n"
                + "    name VARCHAR(100) NOT NULL,\n"
                + "    PRIMARY KEY (id)\n"
                + ")",
                "CREATE SEQUENCE measure_id_gen AS INTEGER START WITH 1 INCREMENT BY 1",
                "ALTER SEQUENCE measure_id_gen OWNED BY measure.id",
                "ALTER TABLE measure ADD UNIQUE (name)",
                "COMMENT ON TABLE measure IS 'Мера измерения'",
                "COMMENT ON COLUMN measure.id IS 'Мера измерения ИД'",
                "COMMENT ON COLUMN measure.name IS 'Наименование'"
        };
        for (String sqlStatement : sqlStatements) {
            DatabaseUtils.executeSql(sqlStatement, jdbcTemplate);
        }
    }

    @Override
    public int load() {
        insert(new Measure(-1, "Без меры измерения"));
        insert(new Measure(1, "Расстояние"));
        insert(new Measure(2, "Вес"));
        insert(new Measure(3, "Время"));
        insert(new Measure(4, "Сила электрического тока"));
        insert(new Measure(5, "Температура"));
        insert(new Measure(6, "Количество вечества"));
        insert(new Measure(7, "Сила света"));
        insert(new Measure(11, "Плоский угол"));
        insert(new Measure(12, "Телесный угол"));
        insert(new Measure(13, "Температура по шкале Цельсия"));
        insert(new Measure(14, "Частота"));
        insert(new Measure(15, "Сила"));
        insert(new Measure(16, "Энергия"));
        insert(new Measure(17, "Мощность"));
        insert(new Measure(18, "Давление"));
        insert(new Measure(19, "Световой поток"));
        insert(new Measure(20, "Освещенность"));
        insert(new Measure(21, "Электрический заряд"));
        insert(new Measure(22, "Разность потенциалов"));
        insert(new Measure(23, "Сопротивление"));
        insert(new Measure(24, "Электроёмкость"));
        insert(new Measure(25, "Магнитный поток"));
        insert(new Measure(26, "Магнитная индукция"));
        insert(new Measure(27, "Индуктивность"));
        insert(new Measure(28, "Электрическая проводимость"));
        insert(new Measure(29, "Активность (радиоактивного источника)"));
        insert(new Measure(30, "Поглощённая доза ионизирующего излучения"));
        insert(new Measure(31, "Эффективная доза ионизирующего излучения"));
        insert(new Measure(32, "Активность катализатора"));
        logger.info("32 measures loaded");
        DatabaseUtils.setSequence("measure_id_gen", 33, jdbcTemplate);
        return 31;
    }


}
