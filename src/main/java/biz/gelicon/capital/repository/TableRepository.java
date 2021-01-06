package biz.gelicon.capital.repository;

import biz.gelicon.capital.utils.JpaUtils;
import biz.gelicon.capital.utils.TableMetadata;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TableRepository<T> {

    Map<String, TableMetadata>  tableMetadataMap = new HashMap<>();// Коллекция из метаданных для таблиц

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    // Удаление записи по умолчанию
    // Необходимы аннотации @Table(name), @Id, @Column(name)
    default int delete(Integer id) {
        Class cls = JpaUtils.getClassGenericInterfaceAnnotationTable(this);
        String tableName = JpaUtils.getTableName(cls); // Ключем является имя класса
        TableMetadata tableMetadata = tableMetadataMap.get(tableName); // Получим из коллекции
        if (tableMetadata == null) { // В коллекции не было
            tableMetadata = new TableMetadata(); // Создаем
            tableMetadata.loadTableMetadata(cls); // Получим все метаданные
            tableMetadataMap.put(tableName,tableMetadata); // Загрузим в воллекцию
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
        String tableName = JpaUtils.getTableName(t); // Ключем является имя класса
        TableMetadata tableMetadata = tableMetadataMap.get(tableName); // Получим из коллекции
        if (tableMetadata == null) { // В коллекции не было
            tableMetadata = new TableMetadata(); // Создаем
            tableMetadata.loadTableMetadata(t.getClass()); // Получим все метаданные
            tableMetadataMap.put(tableName,tableMetadata); // Загрузим в воллекцию
        }
        // Получим значение первичного ключа
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


