package biz.gelicon.capital.repository;

import biz.gelicon.capital.exceptions.BadPagingException;
import biz.gelicon.capital.exceptions.FetchQueryException;
import biz.gelicon.capital.utils.ColumnMetadata;
import biz.gelicon.capital.utils.ConvertUnils;
import biz.gelicon.capital.utils.DatabaseUtils;
import biz.gelicon.capital.utils.JpaUtils;
import biz.gelicon.capital.utils.ResultSetRowMapper;
import biz.gelicon.capital.utils.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import static biz.gelicon.capital.utils.DatabaseUtils.isPostgreSQL;

/**
 * Интерфейс работы с @Table. <p> Реализует дефолтные методы: int count() int insert(T t) int
 * update(T t) int delete(Integer id) int deleteAll() int delete(T t) int insertOrUpdate(T t) int
 * set(T t) List<T> findAll() T findById(Integer id) <p> Для работы должна быть заполнена
 * Map<String, TableMetadata> tableMetadataMap
 */
@Transactional(propagation = Propagation.REQUIRED)
public interface TableRepository<T> {

    Logger logger = LoggerFactory.getLogger(TableRepository.class);
    Boolean logFlag = true;

    /**
     * Коллекция из метаданных для таблиц <p> Должна быть заполнена при запуске программы для всех
     * аннотированных @Table <p> Необходимы аннотации @Table(name), @Id, @Column(name)
     */
    Map<String, TableMetadata> tableMetadataMap = new HashMap<>();

    /**
     * Возвращает количество записей в таблице
     */
    default int count() {
        // Имя класса - из дженерика
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls);
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        Integer i = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + tableName,
                Integer.class);
        return Objects.requireNonNullElse(i, 0);
    }

    /**
     * Добавление записи
     *
     * @param t Объект
     * @return код успеха
     */
    default int insert(T t) {
        String tableName = JpaUtils.getTableName(t); // Ключом является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        Method methodSet = null;
        Integer idSave = id;
        if (id == null) {
            // Сгенерируем значение
            JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
            id = DatabaseUtils.getSequenceNextValue(
                    tableName + "_id_gen",
                    jdbcTemplate
            );
            // Установим у t
            // Получим имя pk
            String key = tableMetadata.getIdFieldName();
            // Найдем сеттер для поля (по имени в базе данных)
            methodSet = tableMetadata.getColumnMetadataList().stream()
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
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
                JpaUtils.getNamedParameterJdbcTemplate();
        StringBuilder sqlTextTop = new StringBuilder(
                "INSERT INTO " + tableMetadata.getTableName() + " (");
        StringBuilder sqlTextBotom = new StringBuilder(") VALUES (");
        String comma = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            sqlTextTop.append(comma).append(tableMetadata.getColumnMetadataList().get(i)
                    .getColumnName());
            sqlTextBotom.append(comma).append(":")
                    .append(tableMetadata.getColumnMetadataList().get(i)
                            .getField().getName());
            if (comma.equals("")) { comma = ", "; }
        }
        String sqlText = sqlTextTop + sqlTextBotom.toString() + ")";
        try {
            return namedParameterJdbcTemplate.update(sqlText,
                    new BeanPropertySqlParameterSource(t));
        } catch (Exception e) {
            if (idSave == null) { // Надо восстановить пустоту в первичном ключе
                if (methodSet != null) { // Сеттер есть
                    try {
                        methodSet.invoke(t, idSave); // Вызовем для t с параметром из value
                    } catch (Exception ex) {
                        String errText = String
                                .format("Invoke method %s failed ", methodSet.toString());
                        logger.error(errText, e);
                        throw new RuntimeException(errText, ex);
                    }
                }
            }
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
            //throw new PostRecordException(errText, e);
        }
    }

    /**
     * Изменение записи
     *
     * @param t Объект
     * @return код успеха
     */
    default int update(T t) {
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        StringBuilder sqlText = new StringBuilder(
                "UPDATE " + tableMetadata.getTableName() + " SET ");
        String comma = "";
        String idName = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            if (!tableMetadata.getColumnMetadataList().get(i).getIdFlag()) {
                sqlText.append(comma).append(tableMetadata.getColumnMetadataList().get(i)
                        .getColumnName());
                sqlText.append(" = :")
                        .append(tableMetadata.getColumnMetadataList().get(i).getField()
                                .getName());
                if (comma.equals("")) { comma = ", "; }
            } else {
                idName = tableMetadata.getColumnMetadataList().get(i).getField().getName();
            }
        }
        sqlText.append(" WHERE ").append(tableMetadata.getIdFieldName()).append(" = :")
                .append(idName);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JpaUtils
                .getNamedParameterJdbcTemplate();
        try {
            return namedParameterJdbcTemplate.update(sqlText.toString(),
                    new BeanPropertySqlParameterSource(t));
        } catch (Exception e) {
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
    }

    /**
     * Удаление записи по первичному ключу. <p> Если вызвать с id = -123 то удалит все записи
     *
     * @param id значение первичного ключа
     * @return код успеха
     */
    default int delete(Integer id) {
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName();
        if (id != -123) {
            sqlText = sqlText + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        }
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        try {
            return jdbcTemplate.update(sqlText);
        } catch (Exception e) {
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
    }

    /**
     * Удаление всех записей из таблицы
     */
    default int deleteAll() {
        return delete(-123);
    }

    /**
     * Удаление записи. <p> Должо быть установлено поле - первичный ключ
     *
     * @param t Объект
     * @return код успеха
     */
    default int delete(T t) {
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        // Составим текст удаления
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName()
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        // Выполним удаление
        try {
            return JpaUtils.getJdbcTemplate().update(sqlText);
        } catch (Exception e) {
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
    }

    /**
     * Добавление или изменение записи в зависимости от id
     *
     * @param t Объект
     * @return код успеха
     */
    default int insertOrUpdate(T t) {
        if (t == null) {return -1;}
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        if (id == null) {
            return insert(t);
        } else {
            return update(t);
        }
    }

    /**
     * Добавление или изменение записи в зависимости от наличия в базе. <p> Наличие проверяет по
     * первичному ключу (если не null). Если пк = null или если записи по пк нет в базе -
     * добавляет.
     *
     * @param t Объект
     * @return код успеха
     */
    default int set(T t) {
        if (t == null) {return -1;}
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        // Получим значение первичного ключа
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        if (id == null || findById(id) == null) {
            return insert(t);
        } else {
            return update(t);
        }
    }

    /**
     * Возвращает все записи из таблицы без условия
     *
     * @return коллекция из объектов
     */
    default List<T> findAll() {
        return findAll(null);
    }

    /**
     * Возвращает записи из таблицы в соответствие с page
     *
     * @param page
     * @return
     */
    default List<T> findAll(Pageable page) {
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);

        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        // Сформируем текст запроса
        StringBuilder sqlTextBuilder = new StringBuilder("SELECT ");
        String comma = "";
        for (int i = 0; i < tableMetadata.getColumnMetadataList().size(); i++) {
            sqlTextBuilder.append(comma)
                    .append(tableMetadata.getColumnMetadataList().get(i).getColumnName());
            if (comma.equals("")) { comma = ", "; }
        }
        sqlTextBuilder.append(" FROM ").append(tableName);
        if (page != null) {
            // Заменим описания полей с сортировке на имена колонок в базе
            StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(
                            page.getSort().iterator(),
                            Spliterator.ORDERED),
                    false)
                    .forEach(s -> {
                        System.out.println(s.getProperty());
                        if (s.getProperty().equals("shortName")) {
                        }
                    });

            String orderBy = ConvertUnils.buildOrderByFromPegable(page);
            String limit = ConvertUnils.buildLimitFromPegable(page);
            if (orderBy != null) {sqlTextBuilder.append(" ").append(orderBy);}
            if (limit != null) { sqlTextBuilder.append(" ").append(limit);}
            // Если есть пагинация, но нет сортировки - ошибка
            if (limit != null && orderBy == null) {
                // Вызовем наше исключение
                String errText = "SQL build error: " + sqlTextBuilder.toString();
                logger.error(errText);
                throw new BadPagingException(errText);
            }
        }
        String sqlText = sqlTextBuilder.toString();
        // Маппер с классом для модели
        ResultSetRowMapper resultSetRowMapper = new ResultSetRowMapper(cls);
        try {
            JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
            List<T> tList = jdbcTemplate.query(sqlText, resultSetRowMapper);
            return tList;
        } catch (Exception e) {
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            try {
                throw new FetchQueryException(errText, e);
            } catch (FetchQueryException fetchQueryException) {
                throw new RuntimeException(errText, e);
            }
        }
    }

    /**
     * Возвращает запись по id
     *
     * @param id - значение первичного ключа
     * @return объект
     */
    default T findById(Integer id) {
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        // Найдем в коллекции описание таблицы по имени
        TableMetadata tableMetadata = tableMetadataMap.get(tableName);
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        String sqlText = " SELECT * FROM " + tableName
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        // Маппер с классом для модели
        ResultSetRowMapper resultSetRowMapper = new ResultSetRowMapper(cls);
        List<T> tList = null;
        try {
            tList = jdbcTemplate.query(sqlText, resultSetRowMapper);
        } catch (Exception e) {
            String errText = "SQL execute filed: " + sqlText;
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
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

    /**
     * Удаление таблицы из базы данных Удаляет так же последовательность для PostgreSQL Если
     * существуют
     */
    default void drop() {
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        if (DatabaseUtils.checkTableExist(tableName, jdbcTemplate)) {
            DatabaseUtils.executeSql("DROP TABLE " + tableName, jdbcTemplate);
            logger.info("Table " + tableName + " dropped");
        }
        if (isPostgreSQL(jdbcTemplate)) {
            String sequenceName = tableName + "_id_gen";
            // Удалим последовательность
            if (DatabaseUtils.checkSequenceExist(sequenceName, jdbcTemplate)) {
                DatabaseUtils.executeSql(" DROP SEQUENCE " + sequenceName, jdbcTemplate);
                logger.info("Sequence " + sequenceName + " dropped");
            }
        }
    }

    /**
     * Дефайлтный метод создания таблицы СОздает таблицу без полей, без последовательностей, без
     * ничего
     */
    default void create() {
        // Получим класс дженерика класса
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        String sqlText = "CREATE TABLE " + tableName + "()";
        DatabaseUtils.executeSql(sqlText, jdbcTemplate);
        logger.info("Table " + tableName + " created");
    }

    /**
     * Дефаустный метод первоначальной загрузки ВОзвращает количество добавленных записей
     *
     * @return
     */
    default int load() {
        logger.info("0 record loaded");
        return 0;
    }

}



