package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.entity.InventoryHistoryDetailModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryHistoryDetailResponse {
    private String name;
    private String description;
    private LocalDateTime reportedDate;
    private Integer reportedBy;
    private List<InventoryHistoryDetailModel> inventoryHistoryDetailList;
}
