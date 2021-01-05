package biz.gelicon.capital.utils;

import biz.gelicon.capital.CapitalApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.thymeleaf.expression.Lists;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class JpaUtils {

    // Возвращает имя таблицы у объекта аннотированного как @Table
    public static String getTableNameOfClass(Object o) {
        Class cls = o.getClass();
        if (cls.isAnnotationPresent(Table.class)) { // Проверим аннотацию Table
            // Если есть - получим имя таблицы
            Table tableAnnotation = (Table) cls.getAnnotation(Table.class);
            return tableAnnotation.name();
        }
        return null;
    }

    // Возвращает Поле аннотированного как @Id и @Column
    public static Field getIdFieldOfClass(Object o) {
        Class cls = o.getClass();
        return Arrays.stream(cls.getDeclaredFields())
                .filter(c -> c.isAnnotationPresent(Id.class)) // Проверим аннотацию Id
                .filter(c -> c.isAnnotationPresent(Column.class)) // Проверим аннотацию Column
                .findFirst()
                .orElse(null);
    }

    // Возвращает имя поля в базе данных из поля объекта аннотированного @Column
    public static String getColumnName(Field f) {
        if (f.isAnnotationPresent(Column.class)) {
            return ((Column) f.getAnnotation(Column.class)).name();
        }
        return null;
    }

    // Возвращает значение первичного ключа из поля объекта
    public static Integer getIdValueIntegerOfField(Field f, Object o) {
        f.setAccessible(true); // Дадим доступ к полю
        try {
            return (Integer) f.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Возвращает созданную из контекста JdbcTemplate
    public static JdbcTemplate getJdbcTemplate() {
        DataSource dataSource = CapitalApplication.getApplicationContext()
                .getBean(DataSource.class);
        return new JdbcTemplate(dataSource);
    }

}
