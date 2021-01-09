package biz.gelicon.capital.repository;

import biz.gelicon.capital.utils.ColumnMetadata;
import biz.gelicon.capital.utils.DatebaseUtils;
import biz.gelicon.capital.utils.JpaUtils;
import biz.gelicon.capital.utils.ResultSetRowMapper;
import biz.gelicon.capital.utils.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Transactional(propagation = Propagation.REQUIRED)
public interface TableRepository<T> {

    Logger logger = LoggerFactory.getLogger(TableRepository.class);
    Boolean logFlag = true;

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
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                JpaUtils.getNamedParameterJdbcTemplate();
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
        int result = -1;
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

    // Добавление или изменение записи в зависимости от id
    default int insertOrUpdate(T t) {
        if (t == null) {return -1;}
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                t.getClass()
        );
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        if (id == null) {
            // Сгенерируем значение
            JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
            id = DatebaseUtils.getSequenceNextValue(
                    tableName + "_id_gen",
                    jdbcTemplate
            );
            // Установим у t
            // Получим имя pk
            String key = tableMetadata.getIdFieldName();
            // Найдем сеттер для поля (по имени в базе данных)
            Method methodSet = tableMetadata.getColumnMetadataList().stream()
                    .filter(c -> c.getColumnName().equals(key))
                    .findAny()
                    .map(ColumnMetadata::getMethodSet)
                    .orElse(null);
            if (methodSet != null) { // Сеттер есть
                try {
                    methodSet.invoke(t, id); // Вызовем для t с параметром из value
                } catch (IllegalAccessException e) {
                    String errText = String
                            .format("Invoke method %s failed - access error", methodSet.toString());
                    logger.error(errText, e);
                    throw new RuntimeException(errText, e);
                } catch (InvocationTargetException e) {
                    String errText = String
                            .format("Invoke method %s failed - target error", methodSet.toString());
                    logger.error(errText, e);
                    throw new RuntimeException(errText, e);
                }
            }
            return insert(t);
        } else {
            return update(t);
        }
    }

    // Добавление или изменение записи в зависимости от наличия в базе
    default int set(T t) {
        if (t == null) {return -1;}
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                t.getClass()
        );
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        if (id == null || findById(id) == null) {
            return insert(t);
        } else {
            return update(t);
        }
    }

    default List<T> findAll() { // Все записи
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                cls
        );
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        // Сформируем текст запроса
        String sqlText = "SELECT ";
        String comma = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            sqlText = sqlText
                    + comma + tableMetadata.getColumnMetadataList().get(i).getColumnName();
            if (comma.equals("")) { comma = ", "; }
        }
        sqlText = sqlText + " FROM " + tableName;
        // Маппер с классом для модели
        ResultSetRowMapper resultSetRowMapper = new ResultSetRowMapper(cls);
        List<T> tList = jdbcTemplate.query(sqlText, resultSetRowMapper);
        return tList;
    }

    default T findById(Integer id) { // Запись по id
        // https://www.codeflow.site/ru/article/spring__spring-jdbctemplate-querying-examples
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                tableMetadataMap,
                cls
        );
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        String sqlText = " SELECT * FROM " + tableName
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        // Маппер с классом для модели
        ResultSetRowMapper resultSetRowMapper = new ResultSetRowMapper(cls);
        List<T> tList = jdbcTemplate.query(sqlText, resultSetRowMapper);
        if (tList.size() == 0) {
            return null;
        }
        if (tList.size() > 1) {
            String errText = String.format("Too many rows for %s", sqlText);
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        return (T) tList.get(0);
    }
}


