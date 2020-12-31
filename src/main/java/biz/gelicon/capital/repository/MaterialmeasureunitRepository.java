package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Materialmeasureunit;

import java.util.List;

public interface MaterialmeasureunitRepository {

    int count();

    int insert(Materialmeasureunit measure);

    int update(Materialmeasureunit measure);

    int delete(Integer id);

    List<Materialmeasureunit> findAll();

    Materialmeasureunit findById(Integer id);
}
