package biz.gelicon.capital.utils;

import java.lang.reflect.Field;

public class TableMetadata {

    String tableName; // Имя таблицы в базе данных
    Field idField; // Первичный ключ
    String idFieldName; // Имя поля первичного
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
    }

}
