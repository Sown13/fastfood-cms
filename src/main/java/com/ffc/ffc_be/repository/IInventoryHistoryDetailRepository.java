package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.InventoryHistoryDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryHistoryDetailRepository extends JpaRepository<InventoryHistoryDetailModel, Integer> {
}
