package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.MenuDishDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuDishDetailRepository extends JpaRepository<MenuDishDetailModel, Integer> {
    List<MenuDishDetailModel> findAllByMenuDishId(Integer menuId);
}
