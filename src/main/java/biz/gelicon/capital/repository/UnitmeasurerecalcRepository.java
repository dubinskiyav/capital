package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Unitmeasurerecalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
