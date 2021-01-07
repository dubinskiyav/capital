package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materiallevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MateriallevelRepository implements TableRepository<Materiallevel>{

    private static final Logger logger = LoggerFactory.getLogger(MateriallevelRepository.class);
    private boolean logFlag = false;

    // Spring Boot создаст и отконфигурирует DataSource и JdbcTemplate
    // для этого добавить @Autowired
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Materiallevel> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        master_id, "
                        + "        name, "
                        + "        code, "
                        + "        date_beg, "
                        + "        date_end "
                        + " FROM   materiallevel "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Materiallevel(
                                rs.getInt("id"),
                                rs.getInt("master_id"),
                                rs.getString("name"),
                                rs.getString("code"),
                                rs.getDate("date_beg"),
                                rs.getDate("date_end")
                        )
        );
    }

    @Override
    public Materiallevel findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        master_id, "
                + "        name, "
                + "        code, "
                + "        date_beg, "
                + "        date_end "
                + " FROM   materiallevel "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Materiallevel(
                                rs.getInt("id"),
                                rs.getInt("master_id"),
                                rs.getString("name"),
                                rs.getString("code"),
                                rs.getDate("date_beg"),
                                rs.getDate("date_end")
                        )
        );
    }
}
