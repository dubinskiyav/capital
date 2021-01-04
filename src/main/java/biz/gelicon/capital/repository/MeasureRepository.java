package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;
import biz.gelicon.capital.utils.DatebaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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

    @Override
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   measure ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Measure measure) {
        if (logFlag) {
            logger.info("Saving...{}", measure.toString());
        }
        // Установим следующее значение id
        measure.setId(DatebaseUtils.getSequenceNextValue("measure_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO measure ("
                        + "   id, "
                        + "   name "
                        + " ) VALUES(?,?)",
                measure.getId(),
                measure.getName()
        );
        return result;
    }

    @Override
    public int update(Measure measure) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE measure SET "
                        + "   name = :name "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(measure));
        return result;
    }

    @Override
    public List<Measure> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        name "
                        + " FROM   measure "
                        + " ORDER BY name ",
                (rs, rowNum) ->
                        new Measure(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
        );
    }

    @Override
    public Measure findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        name "
                + " FROM   measure "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Measure(
                                rs.getInt("id"),
                                rs.getString("name")
                        )
        );
    }
}
