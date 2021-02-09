package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;
import biz.gelicon.capital.model.Unitmeasure;
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
    public void create() {
        Resource resource = new ClassPathResource("sql/500300-measureunit.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("Measureunit created");
    }

    @Override
    public int load() {
        insert(new Measureunit(-1, -1, -1, 1)); // Без единицы
        if (false) {
            insert(new Measureunit(1, 1, 1, 1)); // Расстояние - метр
            insert(new Measureunit(2, 2, 2, 1)); // Вес - Килограмм
            insert(new Measureunit(3, 3, 3, 1)); // Время - секунда
            insert(new Measureunit(4, 4, 4, 1)); // Сила тока - ампер
            insert(new Measureunit(5, 5, 5, 2)); // Температура - Кельвин
            insert(new Measureunit(6, 6, 6, 1)); // Количество вечества - Моль
            insert(new Measureunit(7, 7, 7, 1)); // Сила света - Кандела
            insert(new Measureunit(10, 1, 11, 2)); // Расстояние - Сантиметр
            insert(new Measureunit(11, 1, 12, 2)); // Расстояние - Километр
            insert(new Measureunit(12, 1, 13, 2)); // Расстояние - Миллиметр
            insert(new Measureunit(13, 1, 14, 3)); // Расстояние - Миля
            insert(new Measureunit(14, 1, 15, 3)); // Расстояние - Ярд
            insert(new Measureunit(15, 1, 16, 3)); // Расстояние - Фут
            insert(new Measureunit(16, 1, 17, 3)); // Расстояние - Дюйм
            insert(new Measureunit(20, 2, 18, 2)); // Вес - Грамм
            insert(new Measureunit(21, 2, 19, 2)); // Вес - Тонна
            insert(new Measureunit(22, 2, 20, 2)); // Вес - Центнер
            insert(new Measureunit(23, 2, 21, 3)); // Вес - Карат
            insert(new Measureunit(24, 2, 22, 3)); // Вес - Фунт
            insert(new Measureunit(25, 2, 23, 3)); // Вес - Унция
            insert(new Measureunit(30, 3, 24, 2)); // Время - Год
            insert(new Measureunit(31, 3, 25, 2)); // Время - Квартал
            insert(new Measureunit(32, 3, 26, 2)); // Время - Месяц
            insert(new Measureunit(33, 3, 27, 2)); // Время - Декада
            insert(new Measureunit(34, 3, 28, 2)); // Время - Неделя
            insert(new Measureunit(35, 3, 29, 2)); // Время - Сутки
            insert(new Measureunit(36, 3, 30, 2)); // Время - Час
            insert(new Measureunit(37, 3, 31, 2)); // Время - Минута
            insert(new Measureunit(38, 3, 32, 2)); // Время - Миллисекунда
            insert(new Measureunit(40, 4, 33, 2)); // Сила электрического тока - Миллиампер
            insert(new Measureunit(60, 5, 34, 1)); // Температура - Градус Цельсия
        }
        DatabaseUtils.setSequence("measureunit_id_gen", 61, jdbcTemplate);
        logger.info("Measureunit loaded");
        return 0;
    }

}
