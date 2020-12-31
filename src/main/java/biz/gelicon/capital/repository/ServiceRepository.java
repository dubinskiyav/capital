package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Service;

import java.util.List;

public interface ServiceRepository {

    int count();

    int insert(Service measure);

    int update(Service measure);

    int delete(Integer id);

    List<Service> findAll();

    Service findById(Integer id);
}
