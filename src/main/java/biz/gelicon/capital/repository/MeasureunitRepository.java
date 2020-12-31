package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunit;

import java.util.List;

public interface MeasureunitRepository {

    int count();

    int insert(Measureunit measure);

    int update(Measureunit measure);

    int delete(Integer id);

    List<Measureunit> findAll();

    Measureunit findById(Integer id);
}
