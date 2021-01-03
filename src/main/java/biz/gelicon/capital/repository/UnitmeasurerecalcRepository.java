package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Unitmeasurerecalc;
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
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   unitmeasurerecalc ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Unitmeasurerecalc unitmeasurerecalc) {
        if (logFlag) {
            logger.info("Saving...{}", unitmeasurerecalc.toString());
        }
        // Установим следующее значение id
        unitmeasurerecalc.setId(DatebaseUtils.getSequenceNextValue("unitmeasurerecalc_id_gen", jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO unitmeasurerecalc ("
                        + "   id, "
                        + "   unitmeasurefrom_id, "
                        + "   unitmeasureto_id, "
                        + "   factor "
                        + " ) VALUES(?,?,?,?)",
                unitmeasurerecalc.getId(),
                unitmeasurerecalc.getunitmeasurefromId(),
                unitmeasurerecalc.getunitmeasuretoId(),
                unitmeasurerecalc.getfactor()
        );
        return result;
    }

    @Override
    public int update(Unitmeasurerecalc unitmeasurerecalc) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE unitmeasurerecalc SET "
                        + "   unitmeasurefrom_id = :unitmeasurefromId, "
                        + "   unitmeasureto_id = :unitmeasuretoId, "
                        + "   factor = :factor "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(unitmeasurerecalc));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM unitmeasurerecalc "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public List<Unitmeasurerecalc> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        unitmeasurefrom_id, "
                        + "        unitmeasureto_id, "
                        + "        factor "
                        + " FROM   unitmeasurerecalc ",
                (rs, rowNum) ->
                        new Unitmeasurerecalc(
                                rs.getInt("id"),
                                rs.getInt("unitmeasurefrom_id"),
                                rs.getInt("unitmeasureto_id"),
                                rs.getFloat("factor")
                        )
        );
    }

    @Override
    public Unitmeasurerecalc findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        unitmeasurefrom_id, "
                + "        unitmeasureto_id, "
                + "        factor "
                + " FROM   unitmeasurerecalc "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Unitmeasurerecalc(
                                rs.getInt("id"),
                                rs.getInt("unitmeasurefrom_id"),
                                rs.getInt("unitmeasureto_id"),
                                rs.getFloat("factor")
                        )
        );
    }
}
