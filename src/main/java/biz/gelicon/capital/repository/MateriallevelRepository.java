package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materiallevel;
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
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   materiallevel ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Materiallevel materiallevel) {
        if (logFlag) {
            logger.info("Saving...{}", materiallevel.toString());
        }
        // Установим следующее значение id
        materiallevel.setId(DatebaseUtils.getSequenceNextValue("materiallevel_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO materiallevel ("
                        + "   id, "
                        + "   master_id, "
                        + "   name, "
                        + "   code, "
                        + "   date_beg, "
                        + "   date_end "
                        + " ) VALUES(?,?,?,?,?,?)",
                materiallevel.getId(),
                materiallevel.getMasterId(),
                materiallevel.getName(),
                materiallevel.getCode(),
                materiallevel.getDateBeg(),
                materiallevel.getDateEnd()
        );
        return result;
    }

    @Override
    public int update(Materiallevel materiallevel) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE materiallevel SET "
                        + "   master_id = :masterId, "
                        + "   name = :name, "
                        + "   code = :code, "
                        + "   date_beg = :dateBeg, "
                        + "   date_end = :dateEnd "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(materiallevel));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM materiallevel "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

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
