package biz.gelicon.capital.utils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TableMetadata {

    String tableName; // Имя таблицы в базе данных
    Class modelCls;
    Field idField; // Первичный ключ
    String idFieldName; // Имя поля первичного ключа
    List<ColumnMetadata> columnMetadataList; // Коллекция полей таблицы

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIdFieldName() {
        return this.idFieldName;
    }

    public void setIdFieldName(String idFieldName) {
        this.idFieldName = idFieldName;
    }

    public Field getIdField() {
        return this.idField;
    }

    public void setIdField(Field idField) {
        this.idField = idField;
    }

    public Class getModelCls() {
        return modelCls;
    }

    public void setModelCls(Class modelCls) {
        this.modelCls = modelCls;
    }

    public void loadTableMetadata(Object o) {
        // Получим класс дженерика интерфейса аннотированного как @Table
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(o);
        loadTableMetadata(cls);
    }

    public List<ColumnMetadata> getColumnMetadataList() {
        return this.columnMetadataList;
    }

    public void loadTableMetadata(Class cls) {
        // Получим и запишем имя таблицы
        setTableName(JpaUtils.getTableName(cls));
        // Запишем класс модели
        setModelCls(cls);
        // Найдем поле - первичный ключ и запишем
        setIdField(JpaUtils.getIdField(cls));
        if (getIdField() == null) {
            throw new RuntimeException(
                    "Таблица " + tableName + " не имеет поля, аннотированного как @Id.");
        }
        // Запишем имя поля первичного ключа
        setIdFieldName(JpaUtils.getColumnName(idField));
        // Ищем все методы класса
        Map<String, Method> methodMap = new HashMap<>();
        List<Method> methods = new ArrayList<>();
        methods = Arrays.asList(cls.getMethods());
        for (int i = 0; i < methods.size(); i++) {
            methodMap.put(methods.get(i).getName(),methods.get(i));
        }

        // Ищем все поля помеченные аннотацией @Column
        columnMetadataList = new ArrayList<>();
        List<Field> fieldList = Arrays.stream(cls.getDeclaredFields())
                .filter(c -> c.isAnnotationPresent(Column.class)) // Проверим аннотацию Column
                .collect(Collectors.toList());
        // Записываем их все в коллекцию
        for (Field field : fieldList) {
            ColumnMetadata columnMetadata = new ColumnMetadata();
            columnMetadata.setColumnName(field.getAnnotation(Column.class).name());
            columnMetadata.setNullable(field.getAnnotation(Column.class).nullable());
            columnMetadata.setField(field);
            columnMetadata.setColumnDefinition(field.getAnnotation(Column.class).columnDefinition());
            columnMetadata.setColumn(field.getAnnotation(Column.class));
            columnMetadata.setIdFlag(field.isAnnotationPresent(Id.class));
            // Найдем геттер
            String methodName = "get"
                    + columnMetadata.getField().getName().substring(0,1).toUpperCase()
                    + columnMetadata.getField().getName().substring(1); ;
            if (methodMap.get(methodName) != null) {
                columnMetadata.setMethodGet(methodMap.get(methodName));
            } else { // попробуем найти is
                methodName = "is"
                        + columnMetadata.getField().getName().substring(0,1).toUpperCase()
                        + columnMetadata.getField().getName().substring(1); ;
                if (methodMap.get(methodName) != null) {
                    columnMetadata.setMethodGet(methodMap.get(methodName));
                }
            }
            // Найдем сеттер
            methodName = "set"
                    + columnMetadata.getField().getName().substring(0,1).toUpperCase()
                    + columnMetadata.getField().getName().substring(1); ;
            if (methodMap.get(methodName) != null) {
                columnMetadata.setMethodSet(methodMap.get(methodName));
            }
            columnMetadataList.add(columnMetadata);
        }
    }

    // Возвращает описание таблицы из коллекции.
    // Если описания в коллекции отсутствует - предварительно добавляет его туда
    public static TableMetadata getTableMetadataFromMap(
            String tableName,
            Map<String, TableMetadata> tableMetadataMap,
            Class cls
    ){
        TableMetadata tableMetadata = null;
        if (tableName != null) {
            tableMetadata = tableMetadataMap.get(tableName); // Получим из коллекции
        }
        if (tableMetadata == null) { // В коллекции не было
            tableMetadata = new TableMetadata(); // Создаем
            tableMetadata.loadTableMetadata(cls); // Получим все метаданные
            tableMetadataMap.put(tableMetadata.getTableName(), tableMetadata); // Загрузим в воллекцию
        }
        return tableMetadata;
    }

}
