package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
