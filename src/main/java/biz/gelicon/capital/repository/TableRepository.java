package biz.gelicon.capital.repository;

import biz.gelicon.capital.utils.JpaUtils;
import biz.gelicon.capital.utils.TableMetadata;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.List;

public interface TableRepository<T> {

    TableMetadata tableMetadata = new TableMetadata();

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(Integer id) {
        if (tableMetadata.getTableName() == null) { // Еще не получали метаданные
            // Получим все метеданные
            tableMetadata.loadTableMetadata(this);
        }
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName()
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
        JdbcTemplate jdbcTemplate = JpaUtils.getJdbcTemplate();
        return jdbcTemplate.update(sqlText);
    }

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(T t) {
        if (tableMetadata.getTableName() == null) { // Еще не получали метаданные
            // Получим все метеданные
            tableMetadata.loadTableMetadata(t.getClass());
        }
        String s = tableMetadata.getTableName();
        System.out.println(s);
        // Получим значение
        Integer id = JpaUtils.getIdValueIntegerOfField(tableMetadata.getIdField(), t);
        // Составим текст удаления
        String sqlText = ""
                + " DELETE FROM " + tableMetadata.getTableName()
                + " WHERE " + tableMetadata.getIdFieldName() + " = " + id;
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


