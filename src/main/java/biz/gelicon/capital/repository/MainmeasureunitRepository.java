package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Mainmeasureunit;

import java.util.List;

public interface MainmeasureunitRepository {

    int count();

    int insert(Mainmeasureunit measure);

    int update(Mainmeasureunit measure);

    int delete(Integer id);

    List<Mainmeasureunit> findAll();

    Mainmeasureunit findById(Integer id);
}
