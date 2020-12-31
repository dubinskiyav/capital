package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measureunitrecalc;

import java.util.List;

public interface MeasureunitrecalcRepository {

    int count();

    int insert(Measureunitrecalc measure);

    int update(Measureunitrecalc measure);

    int delete(Integer id);

    List<Measureunitrecalc> findAll();

    Measureunitrecalc findById(Integer id);
}
