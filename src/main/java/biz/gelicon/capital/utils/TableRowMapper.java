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

public class TableRowMapper<T> implements RowMapper {

    static Logger logger = LoggerFactory.getLogger(JpaUtils.class);

    private String tableName;

    public TableRowMapper(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Object newModel(){
        if (getTableName() == null) {
            String errText = "There isn't tableName";
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        // Получим описание таблицы из коллекции
        TableMetadata tableMetadata = TableRepository.tableMetadataMap.get(tableName);
        // Оно уже должно там быть, иначе валимся
        if (tableMetadata == null) {
            String errText = String.format("There isn't tableMetadata for table %s", getTableName());
            logger.error(errText);
            throw new RuntimeException(errText);
        }

        // Создадим объект - модель
        Constructor<?> ctor;
        try {
            ctor = tableMetadata.getModelCls().getConstructor();
        } catch (Exception e) {
            String errText = "cls.getConstructor filed";
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        Object model;
        try {
            model = ctor.newInstance();
        } catch (Exception e) {
            String errText = "setModel(ctor.newInstance()) filed";
            logger.error(errText, e);
            throw new RuntimeException(errText, e);
        }
        return model;
    }

    @Override
    public T mapRow(ResultSet resultSet, int i) throws SQLException {
        // Получим описание таблицы из коллекции
        TableMetadata tableMetadata = TableRepository.tableMetadataMap.get(tableName);
        // Оно уже должно там быть, иначе валимся
        if (tableMetadata == null) {
            String errText = String.format("There isn't tableMetadata for table %s", getTableName());
            logger.error(errText);
            throw new RuntimeException(errText);
        }
        Object model = newModel();
        ResultSetMetaData resultSetMetaData = tableMetadata.getResultSetMetaData();
        // Проверим, если ли уже ResultSetMetaData
        if (resultSetMetaData == null) {
            // считаем
            resultSetMetaData = resultSet.getMetaData();
            // Запишем
            tableMetadata.setResultSetMetaData(resultSetMetaData);
        }
        int columnCount = resultSetMetaData.getColumnCount();
        // Пробежимся по полям результата запроса
        for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
            String key = resultSetMetaData.getColumnName(columnNumber);
            Object value = resultSet.getObject(columnNumber);
            // Найдем сеттер для поля (по имени в базе данных)
            Method methodSet = tableMetadata.getColumnMetadataList().stream()
                    .filter(c -> c.getColumnName().equals(key))
                    .findAny()
                    .map(ColumnMetadata::getMethodSet)
                    .orElse(null);
            if (methodSet != null) { // Сеттер есть
                try {
                    methodSet.invoke(model, value); // Вызовем для t с параметром из value
                } catch (IllegalAccessException e) {
                    String errText = String.format("Invoke method %s failed - access error", methodSet.toString());
                    logger.error(errText, e);
                    throw new RuntimeException(errText, e);
                } catch (InvocationTargetException e) {
                    String errText = String.format("Invoke method %s failed - target error", methodSet.toString());
                    logger.error(errText, e);
                    throw new RuntimeException(errText, e);
                }
            }
        }
        return (T) model;
    }
}
