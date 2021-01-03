package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Unitmeasure;
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
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   unitmeasure ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Unitmeasure measure) {
        if (logFlag) {
            logger.info("Saving...{}", measure.toString());
        }
        // Установим следующее значение id
        measure.setId(DatebaseUtils.getSequenceNextValue("measure_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO unitmeasure ("
                        + "   id, "
                        + "   name, "
                        + "   short_name "
                        + " ) VALUES(?,?)",
                measure.getId(),
                measure.getName(),
                measure.getShortName()
        );
        return result;
    }

    @Override
    public int update(Unitmeasure measure) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE unitmeasure SET "
                        + "   name = :name, "
                        + "   short_name = :shortName "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(measure));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM unitmeasure "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public int insupd(Unitmeasure measure) {
        if (measure == null) {
            return -1;
        }
        if (measure.getId() == null) {
            return insert(measure);
        } else {
            return update(measure);
        }
    }

    @Override
    public List<Unitmeasure> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        name, "
                        + "        short_name "
                        + " FROM   unitmeasure "
                        + " ORDER BY name ",
                (rs, rowNum) ->
                        new Unitmeasure(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("short_name")
                        )
        );
    }

    @Override
    public Unitmeasure findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        name, "
                + "        short_name "
                + " FROM   unitmeasure "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Unitmeasure(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("short_name")
                        )
        );
    }
}
