package biz.gelicon.capital.repository;

import biz.gelicon.capital.model.Material;

import java.util.List;

public interface MaterialRepository {

    int count();

    int insert(Material measure);

    int update(Material measure);

    int delete(Integer id);

    List<Material> findAll();

    Material findById(Integer id);
}
