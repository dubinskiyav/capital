package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
