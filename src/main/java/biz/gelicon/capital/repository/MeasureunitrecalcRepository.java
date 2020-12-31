package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunitrecalc;
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
public class MeasureunitrecalcRepository implements TableRepository<Measureunitrecalc>{

    private static final Logger logger = LoggerFactory.getLogger(MeasureunitrecalcRepository.class);
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
                                + " FROM   measureunitrecalc ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Measureunitrecalc measureunitrecalc) {
        if (logFlag) {
            logger.info("Saving...{}", measureunitrecalc.toString());
        }
        // Установим следующее значение id
        measureunitrecalc.setId(DatebaseUtils.getSequenceNextValue("measureunitrecalc_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO measureunitrecalc ("
                        + "   id, "
                        + "   mainmeasureunit_id, "
                        + "   measureunit_id, "
                        + "   conversion_factor "
                        + " ) VALUES(?,?,?,?)",
                measureunitrecalc.getId(),
                measureunitrecalc.getMainMeasureunitId(),
                measureunitrecalc.getMeasureunitId(),
                measureunitrecalc.getConversionFactor()
        );
        return result;
    }

    @Override
    public int update(Measureunitrecalc measureunitrecalc) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE measureunitrecalc SET "
                        + "   mainmeasureunit_id = :mainmeasureunitIid, "
                        + "   measureunit_id = :measureunitId, "
                        + "   conversion_factor = :conversionFactor "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(measureunitrecalc));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM measureunitrecalc "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public List<Measureunitrecalc> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        mainmeasureunit_id, "
                        + "        measureunit_id "
                        + "        conversion_factor "
                        + " FROM   measureunitrecalc "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Measureunitrecalc(
                                rs.getInt("id"),
                                rs.getInt("mainmeasureunit_id"),
                                rs.getInt("measureunit_id"),
                                rs.getFloat("conversion_factor")
                        )
        );
    }

    @Override
    public Measureunitrecalc findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        mainmeasureunit_id, "
                + "        measureunit_id "
                + "        conversion_factor "
                + " FROM   measureunitrecalc "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Measureunitrecalc(
                                rs.getInt("id"),
                                rs.getInt("mainmeasureunit_id"),
                                rs.getInt("measureunit_id"),
                                rs.getFloat("conversion_factor")
                        )
        );
    }
}
