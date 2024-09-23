package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.MenuDishModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuDishRepository extends JpaRepository<MenuDishModel, Integer> {
    Page<MenuDishModel> findAllByIsActivateTrue(Pageable pageable);

    Page<MenuDishModel> findAllByIsActivateFalse(Pageable pageable);
}
