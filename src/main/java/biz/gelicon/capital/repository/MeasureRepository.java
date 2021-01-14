package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MeasureRepository implements TableRepository<Measure>{

    private static final Logger logger = LoggerFactory.getLogger(MeasureRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public int load() {
        insert(new Measure(1, "Вес"));
        insert(new Measure(2, "Расстояние"));
        insert(new Measure(3, "Время"));
        insert(new Measure(4, "Сила тока"));
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
        logger.info("31 measures loaded");
        return 31;
    }

}
