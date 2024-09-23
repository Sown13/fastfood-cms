package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.MenuDishModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IMenuDishRepository extends JpaRepository<MenuDishModel, Integer> {
    Page<MenuDishModel> findAllByIsActiveTrue(Pageable pageable);

    Page<MenuDishModel> findAllByIsActiveFalse(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE MenuDishModel m SET m.isActive = NOT m.isActive WHERE m.id = :id")//might be red alert but it actually compiled and work well
    int toggleActiveStatus(Integer id);
}
