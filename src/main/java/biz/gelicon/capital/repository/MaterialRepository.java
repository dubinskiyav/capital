package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Material;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialRepository implements TableRepository<Material>{

    private static final Logger logger = LoggerFactory.getLogger(MaterialRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Material> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        materiallevel_id, "
                        + "        name, "
                        + "        code "
                        + " FROM   material "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Material(
                                rs.getInt("id"),
                                rs.getInt("materiallevel_id"),
                                rs.getString("name"),
                                rs.getString("code")
                        )
        );
    }

    @Override
    public Material findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        materiallevel_id, "
                + "        name, "
                + "        code "
                + " FROM   material "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Material(
                                rs.getInt("id"),
                                rs.getInt("materiallevel_id"),
                                rs.getString("name"),
                                rs.getString("code")
                        )
        );
    }
}
