package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.InventoryHistoryDetailModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryHistoryDetailRepository extends JpaRepository<InventoryHistoryDetailModel, Integer> {
    Page<InventoryHistoryDetailModel> findInventoryHistoryDetailModelsByInventoryHistoryId(Integer inventoryHistoryId, Pageable page);
}
