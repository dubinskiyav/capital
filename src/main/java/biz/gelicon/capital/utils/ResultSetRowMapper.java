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

public class ResultSetRowMapper<T> implements RowMapper {

    static Logger logger = LoggerFactory.getLogger(JpaUtils.class);

    private String tableName; // Имя таблицы

    private Class modelCls; // Класс модели

    private Object currModel;

    private ResultSetMetaData resultSetMetaData; // Метаданные результирующего сета

    // Смена класса приводит к пересчету всего
    public void setModelCls(Class modelCls) {
        if (this.modelCls != null && this.modelCls == modelCls) {
            // Класс не сменился - ничего не делаем
            return;
        }
        this.modelCls = modelCls;
        this.tableName = JpaUtils.getTableName(modelCls); // Получим таблицу из класса
        this.resultSetMetaData = null; // Обнулим метаданные рузультирующего сета
        this.currModel = null; // Обнулим текущую моедль данных
    }

    public Class getModelCls() {
        return modelCls;
    }

    public String getTableName() {
        return tableName;
    }

    public Object newModel() {
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
            // считаем
            resultSetMetaData = resultSet.getMetaData();
        }
        int columnCount = resultSetMetaData.getColumnCount();
        // Получим описание таблицы из коллекции
        /*
        TableMetadata tableMetadata = TableRepository.tableMetadataMap.get(tableName);
        // Оно уже должно там быть, иначе валимся
        if (tableMetadata == null) {
            String errText = String
                    .format("There isn't tableMetadata for table %s", getTableName());
            logger.error(errText);
            throw new RuntimeException(errText);
        }
         */
        // Получим описание таблицы
        TableMetadata tableMetadata = TableMetadata.getTableMetadataFromMap(
                tableName,
                TableRepository.tableMetadataMap,
                modelCls
        );
        currModel = newModel();
        // Пробежимся по полям результата запроса
        for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
            String key = resultSetMetaData.getColumnName(columnNumber);
            Object value = resultSet.getObject(columnNumber);
            //logger.info("key=" + key + " value=" + value.toString());
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
        return currModel;
    }
}
