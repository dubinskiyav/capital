package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materialmeasureunit;
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
public class MaterialmeasureunitRepository implements TableRepository<Materialmeasureunit>{

    private static final Logger logger = LoggerFactory.getLogger(MaterialmeasureunitRepository.class);
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
                                + " FROM   materialmeasureunit ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Materialmeasureunit materialmeasureunit) {
        if (logFlag) {
            logger.info("Saving...{}", materialmeasureunit.toString());
        }
        // Установим следующее значение id
        materialmeasureunit.setId(DatebaseUtils.getSequenceNextValue("materialmeasureunit_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO materialmeasureunit ("
                        + "   id, "
                        + "   material_id, "
                        + "   measureunit_id "
                        + " ) VALUES(?,?,?)",
                materialmeasureunit.getId(),
                materialmeasureunit.getMaterialId(),
                materialmeasureunit.getMeasureunitId()
        );
        return result;
    }

    @Override
    public int update(Materialmeasureunit materialmeasureunit) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE materialmeasureunit SET "
                        + "   material_id = :materialId, "
                        + "   measureunit_id = :measureunitId "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(materialmeasureunit));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM materialmeasureunit "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public List<Materialmeasureunit> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT id, "
                        + "        material_id,"
                        + "        measureunit_id "
                        + " FROM   materialmeasureunit "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Materialmeasureunit(
                                rs.getInt("id"),
                                rs.getInt("material_id"),
                                rs.getInt("measureunit_id")
                        )
        );
    }

    @Override
    public Materialmeasureunit findById(Integer id) {
        String sql = ""
                + " SELECT id, "
                + "        material_id,"
                + "        measureunit_id "
                + " FROM   materialmeasureunit "
                + " WHERE  id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Materialmeasureunit(
                                rs.getInt("id"),
                                rs.getInt("material_id"),
                                rs.getInt("measureunit_id")
                        )
        );
    }
}
