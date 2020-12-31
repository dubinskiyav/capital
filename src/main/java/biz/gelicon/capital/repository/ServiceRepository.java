package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Service;
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
public class ServiceRepository implements TableRepository<Service>{

    private static final Logger logger = LoggerFactory.getLogger(ServiceRepository.class);
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
                                + " FROM   service ",
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    @Override
    public int insert(Service service) {
        if (logFlag) {
            logger.info("Saving...{}", service.toString());
        }
        // Установим следующее значение id
        Integer result = -1;
        result = jdbcTemplate.update(""
                        + " INSERT INTO service ("
                        + "   service_id "
                        + " ) VALUES(?)",
                service.getServiceId()
        );
        return result;
    }

    @Override
    public int update(Service service) {
        //Названия параметров должны совпадать с полями
        // и обязательно должны быть геттеры на все поля
        int result = -1;
        result = namedParameterJdbcTemplate.update(""
                        + " UPDATE service SET "
                        + "   service_id = :serviceId "
                        + " WHERE service_id = :serviceId ",
                new BeanPropertySqlParameterSource(service));
        return result;
    }

    @Override
    public int delete(Integer id) {
        int result = -1;
        result = jdbcTemplate.update(""
                        + " DELETE FROM service "
                        + " WHERE id = ? ",
                id
        );
        return result;
    }

    @Override
    public int insupd(Service service) {
        if (service == null) {
            return -1;
        }
        if (service.getServiceId() == null) {
            return insert(service);
        } else {
            return update(service);
        }
    }

    @Override
    public List<Service> findAll() {
        return jdbcTemplate.query(""
                        + " SELECT service_id "
                        + " FROM   service "
                        + " ORDER BY 1 ",
                (rs, rowNum) ->
                        new Service(
                                rs.getInt("service_id")
                        )
        );
    }

    @Override
    public Service findById(Integer id) {
        String sql = ""
                + " SELECT service_id "
                + " FROM   service "
                + " WHERE  service_id = :serviceId ";
        return namedParameterJdbcTemplate.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                (rs, rowNum) ->
                        new Service(
                                rs.getInt("service_id")
                        )
        );
    }
}
