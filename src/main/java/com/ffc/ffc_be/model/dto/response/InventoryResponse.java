package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer materialId;
    private String name;
    private String code;
    private Integer quantity;
    private String unitType;
    private Integer shelfLife;
    private String description;
    private String note;
    private Boolean deprecated;
}
