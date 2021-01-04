package biz.gelicon.capital.repository;

import biz.gelicon.capital.utils.Captable;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.expression.Lists;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public interface TableRepository<T extends IdField> {

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    int delete(Integer id); // Удаление записи

    default int delete(T t) { // Удаление записи общее
        Class<? extends TableRepository> cls = (Class<? extends TableRepository>) t.getClass();
        if (cls.isAnnotationPresent(Captable.class)) { // Проверим существование аннотации Captable
            // Получим ее
            Captable an = (Captable) t.getClass().getAnnotation(Captable.class);
            // Из аннотации вытащим название таблицы и имя первичного ключа и слепим текст удаления
            String sqlText = ""
                    + " DELETE FROM " + an.tableName()
                    + " WHERE " + an.pkName() + " = " + t.getId();
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
        } else if (cls.isAnnotationPresent(Table.class)) { // Проверим аннотацию Table
            // Если есть - получим имя таблицы
            Table tableAnnotation = (Table) t.getClass().getAnnotation(Table.class);
            String tableName = tableAnnotation.name();
            // Найдем первичный ключ - имя поля
            String idName = Arrays.stream(cls.getDeclaredFields())
                    .filter(c -> c.isAnnotationPresent(Id.class)) // Проверим аннотацию Id
                    .filter(c -> c.isAnnotationPresent(Column.class)) // Проверим аннотацию Column
                    .map(c -> {
                        System.out.println(c);
                        Column column = (Column) c.getClass().getAnnotation(Column.class);
                        return column.name();
                    })
                    .findFirst()
                    .orElse("id");
            System.out.println(idName);
        } else {
            System.out.println("нет");
        }

        return -100; // Не найдено удаление
    }

    default int insertOrUpdate(T t) { // Добавление или изменение записи в зависимости от id
        if (t == null) {return -1;}
        if (t.getId() == null) {
            return insert(t);
        } else {
            return update(t);
        }
    }

    default int set(T t) { // Добавление или изменение записи в зависимости от наличия в базе
        if (t == null) {return -1;}
        if (findById(t.getId()) == null) {
            return insert(t);
        } else {
            return update(t);
        }
    }

    List<T> findAll(); // Все записи

    T findById(Integer id); // Запись по id

}
