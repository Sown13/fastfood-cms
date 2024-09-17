package com.ffc.ffc_be.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryHistoryListResponse {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime reportedDate;
    private Integer reportedBy;
    private String reportedByName;
    private String name;
    private String description;
}
