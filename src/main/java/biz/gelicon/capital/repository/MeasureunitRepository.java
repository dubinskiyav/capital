package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;
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
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   measureunit ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Measureunit measureunit) {
        if (logFlag) {
            logger.info("Saving...{}", measureunit.toString());
        }
        // Установим следующее значение id
        measureunit.setId(DatebaseUtils.getSequenceNextValue("measureunit_id_gen", jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO measureunit ("
                        + "   id, "
                        + "   measure_id, "
                        + "   unitmeasure_id, "
                        + "   priority "
                        + " ) VALUES(?,?,?,?)",
                measureunit.getId(),
                measureunit.getMeasureId(),
                measureunit.getUnitmeasureId(),
                measureunit.getPriority()
        );
        return result;
    }

    @Override
    public int update(Measureunit measureunit) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE measureunit SET "
                        + "   measure_id = :measureId, "
                        + "   unitmeasure_id = :unitmeasureId, "
                        + "   priority = :priority "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(measureunit));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM measureunit "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public List<Measureunit> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        measure_id, "
                        + "        unitmeasure_id, "
                        + "        priority "
                        + " FROM   measureunit ",
                (rs, rowNum) ->
                        new Measureunit(
                                rs.getInt("id"),
                                rs.getInt("measure_id"),
                                rs.getInt("unitmeasure_id"),
                                rs.getInt("priority")
                        )
        );
    }

    @Override
    public Measureunit findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        measure_id, "
                + "        unitmeasure_id, "
                + "        priority "
                + " FROM   measureunit "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Measureunit(
                                rs.getInt("id"),
                                rs.getInt("measure_id"),
                                rs.getInt("unitmeasure_id"),
                                rs.getInt("priority")
                        )
        );
    }
}
