package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materialunitmeasure;
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
    public int count() {
        Integer i = jdbcTemplate
                .queryForObject(""
                                + " SELECT COUNT(*) "
                                + " FROM   materialunitmeasure ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Materialunitmeasure materialunitmeasure) {
        if (logFlag) {
            logger.info("Saving...{}", materialunitmeasure.toString());
        }
        // Установим следующее значение id
        materialunitmeasure.setId(DatebaseUtils.getSequenceNextValue("materialunitmeasure_id_gen",jdbcTemplate));
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO materialunitmeasure ("
                        + "   id, "
                        + "   material_id, "
                        + "   unitmeasure_id "
                        + " ) VALUES(?,?,?)",
                materialunitmeasure.getId(),
                materialunitmeasure.getMaterialId(),
                materialunitmeasure.getUnitmeasureId()
        );
        return result;
    }

    @Override
    public int update(Materialunitmeasure materialunitmeasure) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE materialunitmeasure SET "
                        + "   material_id = :materialId, "
                        + "   unitmeasure_id = :unitmeasureId "
                        + " WHERE id = :id ",
                new BeanPropertySqlParameterSource(materialunitmeasure));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM materialunitmeasure "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public int insupd(Materialunitmeasure materialunitmeasure) {
        if (materialunitmeasure == null) {
            return -1;
        }
        if (materialunitmeasure.getId() == null) {
            return insert(materialunitmeasure);
        } else {
            return update(materialunitmeasure);
        }
    }

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
