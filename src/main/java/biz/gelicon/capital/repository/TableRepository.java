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

    default int delete(Integer id) {// Удаление записи
        Type[] genericInterfaces = getClass().getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                Type[] genericTypes = ((ParameterizedType) genericInterface)
                        .getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    if (genericType instanceof Class) {
                        Class cls = (Class) genericType;
                        if (cls.isAnnotationPresent(Table.class)) { // Проверим аннотацию Table
                            // Если есть - получим имя таблицы
                            Table tableAnnotation = (Table) cls.getAnnotation(Table.class);
                            String tableName = tableAnnotation.name();
                            // Найдем поле - первичный ключ
                            Field idField = Arrays.stream(cls.getDeclaredFields())
                                    .filter(c -> c
                                            .isAnnotationPresent(Id.class)) // Проверим аннотацию Id
                                    .filter(c -> c.isAnnotationPresent(
                                            Column.class)) // Проверим аннотацию Column
                                    .findFirst()
                                    .orElse(null);
                            if (idField == null) {
                                throw new RuntimeException(
                                        "У таблицы " + tableName
                                                + " не аннотирован первичный ключ.");
                            }
                            // Найдем первичный ключ - имя поля
                            String idName = ((Column) idField.getAnnotation(Column.class)).name();
                            String sqlText = ""
                                    + " DELETE FROM " + tableName
                                    + " WHERE " + idName + " = " + id;
                            DataSource dataSource = CapitalApplication.getApplicationContext()
                                    .getBean(DataSource.class);
                            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                            return jdbcTemplate.update(sqlText);
                        }
                    }
                }
            }
        }
        return 0;
    }

    default int delete(T t) { // Удаление записи общее
        // Получим имя таблицы в базе данных из аннотации @Table
        String tableName = JpaUtils.getTableNameOfClass(t);
        if (tableName == null) {
            throw new RuntimeException("У объекта нет аннотации @Table");
        }
        // Найдем поле - первичный ключ
        Field idField = JpaUtils.getIdFieldOfClass(t);
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


