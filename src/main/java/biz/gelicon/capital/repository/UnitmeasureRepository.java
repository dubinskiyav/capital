package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Unitmeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
