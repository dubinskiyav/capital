package biz.gelicon.capital.utils;

import biz.gelicon.capital.repository.TableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Пользовательский RowMapper для ResultSet <p> Необходимо установить класс модели, тогда результат
 * будет в виде модели <p> Если модель не установлена - возвращает Map из полей результата, key -
 * имя поля в запросе <p> Пример использования <p> ResultSetRowMapper resultSetRowMapper = new
 * ResultSetRowMapper(cls); <p> List<T> tList = jdbcTemplate.query(sqlText, resultSetRowMapper);
 *
 * @see <a href="https://www.codeflow.site/ru/article/spring__spring-jdbctemplate-querying-examples">Пользовательский
 * RowMapper</a>
 */
public class ResultSetRowMapper implements RowMapper {

    static Logger logger = LoggerFactory.getLogger(ResultSetRowMapper.class);

    private String tableName; // Имя таблицы

    private Class modelCls; // Класс модели

    private Object currModel;

    private TableMetadata tableMetadata;
    private Map<String, Object> stringObjectMap; // Вместо модели, если нет класса

    private ResultSetMetaData resultSetMetaData; // Метаданные результирующего сета

    public ResultSetRowMapper(Class modelCls) {
        this.modelCls = modelCls;
    }

    /**
     * Смена класса модели
     */
    public void setModelCls(Class modelCls) {
        if (modelCls == null) {
            // Все обнулим
            this.modelCls = null;
            this.tableName = null;
            this.resultSetMetaData = null;
            this.currModel = null;
            this.tableMetadata = null;
            return;
        }
        if (this.modelCls != null && this.modelCls == modelCls) {
            // Класс не сменился - ничего не делаем
            return;
        }
        this.modelCls = modelCls;
        this.tableName = JpaUtils.getTableName(modelCls); // Получим имя таблицы из класса
        this.resultSetMetaData = null; // Обнулим метаданные рузультирующего сета
        this.currModel = null; // Обнулим текущую моедль данных
        this.tableMetadata = null;
    }

    public Class getModelCls() {
        return modelCls;
    }

    public String getTableName() {
        return tableName;
    }

    private Object newModel() {
        // Создадим объект - модель
        Constructor<?> ctor;
        try {
            ctor = modelCls.getConstructor();
        } catch (Exception e) {
            String errText = "modelCls.getConstructorr filed";
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        Object model;
        try {
            model = ctor.newInstance();
        } catch (Exception e) {
            String errText = "model = ctor.newInstance()) filed";
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        return model;
    }

    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        // Проверим, если ли уже ResultSetMetaData
        if (resultSetMetaData == null) {
            // считаем из результирующего сета
            resultSetMetaData = resultSet.getMetaData();
        }
        int columnCount = resultSetMetaData.getColumnCount();
        if (modelCls != null) { // Модель установлена
            if (tableMetadata == null) { // Еще не считывали
                // Получим описание таблицы
                tableMetadata = TableMetadata.getTableMetadataFromMap(
                        tableName,
                        TableRepository.tableMetadataMap,
                        modelCls
                );
            }
            currModel = newModel();
        } else {
            stringObjectMap = new HashMap<>();
        }
        // Пробежимся по полям результата запроса
        for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
            String key = resultSetMetaData.getColumnName(columnNumber);
            Object value = resultSet.getObject(columnNumber);
            if (tableMetadata != null) { // или modelCls != null - делаем для модели
                // Найдем сеттер для поля (по имени в базе данных)
                Method methodSet = tableMetadata.getColumnMetadataList().stream()
                        .filter(c -> c.getColumnName().equals(key))
                        .findAny()
                        .map(ColumnMetadata::getMethodSet)
                        .orElse(null);
                if (methodSet != null) { // Сеттер есть
                    try {
                        methodSet.invoke(currModel, value); // Вызовем для t с параметром из value
                    } catch (IllegalAccessException e) {
                        String errText = String.format("Invoke method %s failed - access error",
                                methodSet.toString());
                        logger.error(errText, e);
                        throw new RuntimeException(errText, e);
                    } catch (InvocationTargetException e) {
                        String errText = String.format("Invoke method %s failed - target error",
                                methodSet.toString());
                        logger.error(errText, e);
                        throw new RuntimeException(errText, e);
                    }
                }
            } else { // Модели нет - делаем в stringObjectMap из результата запроса
                stringObjectMap.put(key, value);
            }
        }
        if (tableMetadata != null) { // или modelCls != null - возвращаем модель
            return currModel;
        } else {
            return stringObjectMap;
        }
    }
}
