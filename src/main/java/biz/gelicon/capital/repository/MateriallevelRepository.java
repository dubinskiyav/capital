package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materiallevel;

import java.util.List;

public interface MateriallevelRepository {

    int count();

    int insert(Materiallevel measure);

    int update(Materiallevel measure);

    int delete(Integer id);

    List<Materiallevel> findAll();

    Materiallevel findById(Integer id);
}
