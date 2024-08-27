package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.entity.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInventoryRepository extends JpaRepository<InventoryModel, Integer> {
}
