package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.model.entity.InventoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInventoryRepository extends JpaRepository<InventoryModel, Integer> {
    @Query(nativeQuery = true)
    Page<InventoryResponse> getNewestInventory(Pageable pageable);
}
