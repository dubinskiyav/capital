package biz.gelicon.capital.utils;

import javax.persistence.Column;
import java.lang.reflect.Field;

public class ColumnMetadata {

    String columnName; // Имя поля в базе данных

    String columnDefinition; // Русское описание таблицы

    Boolean nullable; // Возможность хранить NULL

    Boolean idFlag; // флаг ИД

    Field field; // Java поле объекта

    Column column; // Аннотация @Column

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDefinition() {
        return this.columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Column getColumn() {
        return this.column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public Boolean getIdFlag(){
        return this.idFlag;
    }

    public void setIdFlag(Boolean idFlag) {
        this.idFlag = idFlag;
    }

}
