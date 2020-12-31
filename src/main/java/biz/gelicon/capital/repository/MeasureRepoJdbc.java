package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Measure;

import java.util.List;

public class MeasureRepoJdbc implements TableRepo<Measure>{

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int insert(Measure measure) {
        return 0;
    }

    @Override
    public int update(Measure measure) {
        return 0;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public List<Measure> findAll() {
        return null;
    }

    @Override
    public Measure findById(Integer id) {
        return null;
    }
}
