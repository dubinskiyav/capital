package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;

import java.util.List;

// Базовый интерфейс, содержит базовые методы модели
// Все классы работы с моделью должны его реализовывать
public interface MeasureRepository {

    int count();

    int insert(Measure measure);

    int update(Measure measure);

    int delete(Integer id);

    List<Measure> findAll();

    Measure findById(Integer id);
}
