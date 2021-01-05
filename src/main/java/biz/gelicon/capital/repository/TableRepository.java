package biz.gelicon.capital.repository;

import biz.gelicon.capital.CapitalApplication;
import biz.gelicon.capital.utils.JpaUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public interface TableRepository<T> {

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(Integer id) {
        // Получим класс дженерика интерфейса аннотированного как @Table
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        if (cls == null) {
            throw new RuntimeException("Объект не аннотирован как @Table.");
        }
        // Получим имя таблицы
        String tableName = ((Table) cls.getAnnotation(Table.class)).name();
        tableName = JpaUtils.getTableName(cls);
        // Найдем поле - первичный ключ
        Field idField = JpaUtils.getIdField(cls);
        if (idField == null) {
            throw new RuntimeException(
                    "Таблица " + tableName + " не имеет поля, аннотированного как @Id.");
        }
        // Имя поля первичного ключа
        String idName = JpaUtils.getColumnName(idField);
        String sqlText = ""
                + " DELETE FROM " + tableName
                + " WHERE " + idName + " = " + id;
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        return jdbcTemplate.update(sqlText);
    }

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(T t) {
        // Получим имя таблицы в базе данных из аннотации @Table
        String tableName = JpaUtils.getTableName(t);
        if (tableName == null) {
            throw new RuntimeException("У объекта нет аннотации @Table");
        }
        // Найдем поле - первичный ключ
        Field idField = JpaUtils.getIdField(t);
        if (idField == null) {
            throw new RuntimeException(
                    "У таблицы " + tableName + " не аннотирован первичный ключ.");
        }
        // Найдем первичный ключ - имя поля в таблице базы данных
        String idName = JpaUtils.getColumnName(idField);
        // Получим значение
        Integer id = JpaUtils.getIdValueIntegerOfField(idField, t);
        // Составим текст удаления
        String sqlText = ""
                + " DELETE FROM " + tableName
                + " WHERE " + idName + " = " + id;
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

    List<T> findAll(); // Все записи

    T findById(Integer id); // Запись по id

}


