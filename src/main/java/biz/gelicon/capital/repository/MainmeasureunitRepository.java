package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Mainmeasureunit;
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
public class MainmeasureunitRepository implements TableRepository<Mainmeasureunit>{

    private static final Logger logger = LoggerFactory.getLogger(MainmeasureunitRepository.class);
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
                                + " FROM   mainmeasureunit ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Mainmeasureunit mainmeasureunit) {
        if (logFlag) {
            logger.info("Saving...{}", mainmeasureunit.toString());
        }
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO mainmeasureunit ("
                        + "   measureunit_id "
                        + " ) VALUES(?)",
                mainmeasureunit.getMainMeasureunitId()
        );
        return result;
    }

    @Override
    public int update(Mainmeasureunit mainmeasureunit) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE mainmeasureunit SET "
                        + "   measureunit_id = :measureunitId "
                        + " WHERE measureunit_id = :measureunitId ",
                new BeanPropertySqlParameterSource(mainmeasureunit));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM mainmeasureunit "
                        + " WHERE measureunit_id = ? ",
                id
        );
        return result;
    }

    @Override
    public List<Mainmeasureunit> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT measureunit_id "
                        + " FROM   mainmeasureunit "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Mainmeasureunit(
                                rs.getInt("measureunit_id")
                        )
        );
    }

    @Override
    public Mainmeasureunit findById(Integer id) {
        String sql = ""
                + " SELECT measureunit_id "
                + " FROM   mainmeasureunit "
                + " WHERE  measureunit_id = :id ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Mainmeasureunit(
                                rs.getInt("measureunit_id")
                        )
        );
    }
}
