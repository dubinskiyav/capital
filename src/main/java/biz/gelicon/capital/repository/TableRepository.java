package biz.gelicon.capital.repository;

import java.util.List;

public interface TableRepository<T> {

    int count();

    int insert(T t);

    int update(T t);

    int delete(Integer id);

    List<T> findAll();

    T findById(Integer id);

}
