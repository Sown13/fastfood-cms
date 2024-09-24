package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailMaterialDto;
import com.ffc.ffc_be.model.entity.MenuDishDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMenuDishDetailRepository extends JpaRepository<MenuDishDetailModel, Integer> {
    List<MenuDishDetailModel> findAllByMenuDishId(Integer menuId);

    @Query(value = "SELECT new com.ffc.ffc_be.model.dto.puredto.MenuDishDetailMaterialDto(" +
            "mdd.materialId, md.name, mdd.quantity, md.unitType," +
            "mdd.note, mdd.prepareTime)" +
            "FROM MenuDishDetailModel mdd " +
            "JOIN MaterialModel md ON mdd.materialId = md.id " +
            "WHERE mdd.menuDishId = :menuId")
    List<MenuDishDetailMaterialDto> findRecipeMaterialByMenuId(Integer menuId);
}
