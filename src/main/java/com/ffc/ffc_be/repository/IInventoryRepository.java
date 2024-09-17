package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.model.entity.InventoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInventoryRepository extends JpaRepository<InventoryModel, Integer> {
    //Practice with named query
    @Query(nativeQuery = true, name = "InventoryModel.getNewestInventory")
    List<InventoryResponse> getNewestInventoryWithPagination(@Param("limit") int limit, @Param("offset") int offset);

    default Page<InventoryResponse> getNewestInventory(Pageable pageable) {
        int limit = pageable.getPageSize();
        int offset = pageable.getPageNumber() * limit;

        List<InventoryResponse> content = getNewestInventoryWithPagination(limit, offset);
        return new PageImpl<>(content, pageable, content.size());
    }

    Optional<InventoryModel> findByMaterialId(Integer materialId);
}
