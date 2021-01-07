package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;
import biz.gelicon.capital.utils.JpaUtils;
import biz.gelicon.capital.utils.TableMetadata;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional(propagation = Propagation.REQUIRED)
public interface TableRepository<T> {

    Map<String, TableMetadata> tableMetadataMap = new HashMap<>();// Коллекция из метаданных для таблиц

    default int count() { // Количество записей в таблице
        // Имя класса - из дженерика
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls);
        // todo подставить имя таблицы
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        Integer i = jdbcTemplate
                .queryForObject(" SELECT COUNT(*) FROM " + tableName,
                        Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    default int insert(T t) { // Добавление записи
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                t.getClass()
        );
        String sqlTextTop = "INSERT INTO " + tableMetadata.getTableName() + " (";
        String sqlTextBotom = ") VALUES (";
        String comma = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            sqlTextTop = sqlTextTop + comma + tableMetadata.getColumnMetadataList().get(i)
                    .getColumnName();
            sqlTextBotom = sqlTextBotom + comma + ":" + tableMetadata.getColumnMetadataList().get(i)
                    .getField().getName();
            if (comma.equals("")) { comma = ", "; }
        }
        String sqlText = sqlTextTop + sqlTextBotom + ")";
        //System.out.println(sqlText);
        int result = -1;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JpaUtils
                .getNamedParameterJdbcTemplate();
        result = namedParameterJdbcTemplate.update(sqlText,
                new BeanPropertySqlParameterSource(t));
        return result;
    }

    default int update(T t) { // Изменение записи
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                t.getClass()
        );
        String sqlText = "UPDATE " + tableMetadata.getTableName() + " SET ";
        String comma = "";
        String idName = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            if (!tableMetadata.getColumnMetadataList().get(i).getIdFlag()) {
                sqlText = sqlText + comma + tableMetadata.getColumnMetadataList().get(i)
                        .getColumnName();
                sqlText = sqlText + " = :" + tableMetadata.getColumnMetadataList().get(i).getField()
                        .getName();
                if (comma.equals("")) { comma = ", "; }
            } else {
                idName = tableMetadata.getColumnMetadataList().get(i).getField().getName();
            }
        }
        sqlText = sqlText + " WHERE " + tableMetadata.getIdFieldName() + " = :" + idName;
        int result = -1;
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JpaUtils
                .getNamedParameterJdbcTemplate();
        result = namedParameterJdbcTemplate.update(sqlText,
                new BeanPropertySqlParameterSource(t));
        return result;
    }

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(Integer id) {
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                cls
        );
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName();
        if (id != -123) {
            sqlText = sqlText + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        }
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        return jdbcTemplate.update(sqlText);
    }

    // Удаление всех записей из таблицы
    default int deleteAll() {
        return delete(-123);
    }

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(T t) {
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                t.getClass()
        );
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        // Составим текст удаления
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName()
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        // Выполним удаление
        return JpaUtils.getJdbcTemplate().update(sqlText);
    }

    default int insertOrUpdate(T t) { // Добавление или изменение записи в зависимости от id
        if (t == null) {return -1;}
        if (1 == 2) { // todo Сделать
            return insert(t);
        } else {
            return update(t);
        }
    }

    default int set(T t) { // Добавление или изменение записи в зависимости от наличия в базе
        if (t == null) {return -1;}
        if (findById(12345) == null) { // todo Сделать
            return insert(t);
        } else {
            return update(t);
        }
    }

    default List<T> findAll() { // Все записи
        return null;
    }

    default T findById(Integer id) { // Запись по id
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                cls
        );
        // Создадим объект - модель
        Object t = null;
        try {
            Constructor<?> ctor = cls.getConstructor();
            t = ctor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(t.toString());
        // Сформируем текст запроса
        String sqlText = "SELECT ";
        String comma = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            sqlText = sqlText
                    + comma + tableMetadata.getColumnMetadataList().get(i).getColumnName();
            if (comma.equals("")) { comma = ", "; }
        }
        sqlText = sqlText + " FROM " + tableName
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        sqlText = " SELECT * FROM " + tableName
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        BeanPropertyRowMapper beanPropertyRowMapper = new BeanPropertyRowMapper<>(t.getClass());
        try {
            t = jdbcTemplate.queryForObject(sqlText,
                    beanPropertyRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return (T) t;
        // https://www.codeflow.site/ru/article/spring__spring-jdbctemplate-querying-examples
    }
}


