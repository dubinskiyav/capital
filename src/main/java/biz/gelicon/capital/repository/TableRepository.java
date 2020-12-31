package biz.gelicon.capital.repository;

import java.util.List;

public interface TableRepository<T> {

    int count(); // Количество записей в таблице

    int insert(T t); // Добавление записи

    int update(T t); // Изменение записи

    int delete(Integer id); // Удаление записи

    int insupd(T t); // Добавление или изменение записи

    List<T> findAll(); // Все записи

    T findById(Integer id); // Запись по id

}
