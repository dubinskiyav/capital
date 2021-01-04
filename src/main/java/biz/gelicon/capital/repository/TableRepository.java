package biz.gelicon.capital.repository;

import biz.gelicon.capital.CapitalApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public interface TableRepository<T> {

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    int delete(Integer id); // Удаление записи

    default int delete(T t) { // Удаление записи общее
        Class<? extends TableRepository> cls = (Class<? extends TableRepository>) t.getClass();
        String sqlText = null;
        if (cls.isAnnotationPresent(Table.class)) { // Проверим аннотацию Table
            // Если есть - получим имя таблицы
            Table tableAnnotation = (Table) t.getClass().getAnnotation(Table.class);
            String tableName = tableAnnotation.name();
            // Найдем поле - первичный ключ
            Field idField = Arrays.stream(cls.getDeclaredFields())
                    .filter(c -> c.isAnnotationPresent(Id.class)) // Проверим аннотацию Id
                    .filter(c -> c.isAnnotationPresent(Column.class)) // Проверим аннотацию Column
                    .findFirst()
                    .orElse(null);
            if (idField == null) {
                throw new RuntimeException(
                        "У таблицы " + tableName + " не аннотирован первичный ключ.");
            }
            // Найдем первичный ключ - имя поля
            String idName = ((Column) idField.getAnnotation(Column.class)).name();
            // Получим значение
            Integer id = -1;
            idField.setAccessible(true);
            try {
                id = (Integer) idField.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sqlText = ""
                    + " DELETE FROM " + tableName
                    + " WHERE " + idName + " = " + id;
        } else {
            throw new RuntimeException("У таблицы не аннотирован первичный ключ.");
        }
        if (true) {
            DataSource dataSource = CapitalApplication.getApplicationContext()
                    .getBean(DataSource.class);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            return jdbcTemplate.update(sqlText);
        } else {
            // Полдучим поле у класса
            Field field = null;
            try {
                field = this.getClass().getDeclaredField("jdbcTemplate");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            field.setAccessible(true);
            JdbcTemplate jdbcTemplate = null;
            try {
                jdbcTemplate = (JdbcTemplate) field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            int result = -1;
            result = jdbcTemplate.update(sqlText);
            return result;
        }
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
