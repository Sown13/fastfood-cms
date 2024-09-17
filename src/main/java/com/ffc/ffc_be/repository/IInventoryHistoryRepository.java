package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.response.InventoryHistoryListResponse;
import com.ffc.ffc_be.model.entity.InventoryHistoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryHistoryRepository extends JpaRepository<InventoryHistoryModel, Integer> {
    @Query("SELECT new com.ffc.ffc_be.model.dto.response.InventoryHistoryListResponse(" +
            "ihm.id, ihm.createdAt, ihm.createdAt, ihm.reportedDate," +
            "ihm.reportedBy, uci.fullName, ihm.name, ihm.description)" +
            " FROM InventoryHistoryModel ihm" +
            " JOIN UserCmsInfoModel uci ON ihm.reportedBy = uci.id")
    Page<InventoryHistoryListResponse> getInventoryHistoryList(Pageable pageable);
}
