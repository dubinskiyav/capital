package biz.gelicon.capital.utils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TableMetadata {

    String tableName; // Имя таблицы в базе данных
    Field idField; // Первичный ключ
    String idFieldName; // Имя поля первичного ключа
    List<ColumnMetadata> fieldMetadataList; // Коллекция полей таблицы

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

    public void loadTableMetadata(Object o) {
        // Получим класс дженерика интерфейса аннотированного как @Table
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(o);
        loadTableMetadata(cls);
    }

    public void loadTableMetadata(Class cls) {
        // Получим и запишем имя таблицы
        setTableName(JpaUtils.getTableName(cls));
        // Найдем поле - первичный ключ и запишем
        setIdField(JpaUtils.getIdField(cls));
        if (getIdField() == null) {
            throw new RuntimeException(
                    "Таблица " + tableName + " не имеет поля, аннотированного как @Id.");
        }
        // Запишем имя поля первичного ключа
        setIdFieldName(JpaUtils.getColumnName(idField));
        // Ищем все поля помеченные аннотацией @Column
        fieldMetadataList = new ArrayList<>();
        List<Field> fieldList = Arrays.stream(cls.getDeclaredFields())
                .filter(c -> c.isAnnotationPresent(Column.class)) // Проверим аннотацию Column
                .collect(Collectors.toList());
        // Записываем их все в коллекцию
        for (Field field : fieldList) {
            ColumnMetadata fieldMetadata = new ColumnMetadata();
            fieldMetadata.setColumnName(field.getAnnotation(Column.class).name());
            fieldMetadata.setNullable(field.getAnnotation(Column.class).nullable());
            fieldMetadata.setField(field);
            fieldMetadata.setColumnDefinition(field.getAnnotation(Column.class).columnDefinition());
            fieldMetadata.setColumn(field.getAnnotation(Column.class));
            fieldMetadata.setIdFlag(field.isAnnotationPresent(Id.class));
            fieldMetadataList.add(fieldMetadata);
            System.out.println(field.getType());
        }
    }

}
