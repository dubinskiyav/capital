package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materialunitmeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialunitmeasureRepository implements TableRepository<Materialunitmeasure>{

    private static final Logger logger = LoggerFactory.getLogger(MaterialunitmeasureRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Materialunitmeasure> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        material_id,"
                        + "        unitmeasure_id "
                        + " FROM   materialunitmeasure "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Materialunitmeasure(
                                rs.getInt("id"),
                                rs.getInt("material_id"),
                                rs.getInt("unitmeasure_id")
                        )
        );
    }

    @Override
    public Materialunitmeasure findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        material_id,"
                + "        unitmeasure_id "
                + " FROM   materialunitmeasure "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Materialunitmeasure(
                                rs.getInt("id"),
                                rs.getInt("material_id"),
                                rs.getInt("unitmeasure_id")
                        )
        );
    }
}
