package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;
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
public class UnitmeasureRepository implements TableRepository<Unitmeasure>{
    private static final Logger logger = LoggerFactory.getLogger(UnitmeasureRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void create(){
        Resource resource = new ClassPathResource("sql/500200-unitmeasure.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(jdbcTemplate.getDataSource());
        logger.info("Unitmeasure created");
    }

    @Override
    public int load() {
        insert(new Unitmeasure(-1, "Без единицы измерения","-"));
        insert(new Unitmeasure(1, "Метр","м"));
        insert(new Unitmeasure(2, "Килограмм","кг"));
        insert(new Unitmeasure(3, "Секунда","с"));
        insert(new Unitmeasure(4, "Ампер","А"));
        insert(new Unitmeasure(5, "Кельвин","К"));
        insert(new Unitmeasure(6, "Моль","моль"));
        insert(new Unitmeasure(7, "Кандела","кд"));
        insert(new Unitmeasure(11, "Сантиметр","см"));
        insert(new Unitmeasure(12, "Километр","км"));
        insert(new Unitmeasure(13, "Миллиметр","мм"));
        insert(new Unitmeasure(14, "Миля",null));
        insert(new Unitmeasure(15, "Ярд",null));
        insert(new Unitmeasure(16, "Фут",null));
        insert(new Unitmeasure(17, "Дюйм",null));
        insert(new Unitmeasure(18, "Грамм","г"));
        insert(new Unitmeasure(19, "Тонна","тн"));
        insert(new Unitmeasure(20, "Центнер","цт"));
        insert(new Unitmeasure(21, "Карат",null));
        insert(new Unitmeasure(22, "Фунт",null));
        insert(new Unitmeasure(23, "Унция",null));
        insert(new Unitmeasure(24, "Год","г"));
        insert(new Unitmeasure(25, "Квартал","кв"));
        insert(new Unitmeasure(26, "Месяц","м"));
        insert(new Unitmeasure(27, "Декада",null));
        insert(new Unitmeasure(28, "Неделя",null));
        insert(new Unitmeasure(29, "Сутки",null));
        insert(new Unitmeasure(30, "Час","ч"));
        insert(new Unitmeasure(31, "Минута","мин"));
        insert(new Unitmeasure(32, "Миллисекунда","мсек"));
        insert(new Unitmeasure(33, "Миллиампер",null));
        insert(new Unitmeasure(34, "Градус Цельсия","С"));
        insert(new Unitmeasure(35, "Штука","шт"));
        insert(new Unitmeasure(36, "Упаковка",null));
        insert(new Unitmeasure(37, "Контейнер",null));
        insert(new Unitmeasure(38, "Место",null));
        insert(new Unitmeasure(39, "Единица",null));
        insert(new Unitmeasure(40, "Житель",null));
        insert(new Unitmeasure(50, "Метр квадратный","м2"));
        insert(new Unitmeasure(51, "Одна услуга",null));
        logger.info("measure loaded");
        DatabaseUtils.setSequence("unitmeasure_id_gen", 52, jdbcTemplate);
        return 31;
    }

}
