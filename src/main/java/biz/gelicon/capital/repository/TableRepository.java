package biz.gelicon.capital.repository;

import biz.gelicon.capital.utils.Captable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

public interface TableRepository<T extends Id> {

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    int delete(Integer id); // Удаление записи

    default int delete(T t) {
        Class<? extends TableRepository> cls = (Class<? extends TableRepository>) t.getClass();
        if (cls.isAnnotationPresent(Captable.class)) {
            System.out.println("да");
            Captable an = (Captable)t.getClass().getAnnotation(Captable.class);
            String sqlText = ""
                    + " DELETE FROM " + an.tableName()
                    + " WHERE " + an.pkName() + " = " + t.getId();
            if (false) {
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
            } else {

            }
        } else {
            System.out.println("нет");
        }

        return 0;
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
