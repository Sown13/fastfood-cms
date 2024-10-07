package com.ffc.ffc_be.model.dto.response;

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

    private Boolean isExpiredSoon;
    private Integer lifeLeft;

    public InventoryResponse(Integer id, LocalDateTime createdAt, LocalDateTime updatedAt, Integer materialId, String name, String code, Integer quantity, String unitType, Integer shelfLife, String description, String note, Boolean deprecated) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.materialId = materialId;
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.unitType = unitType;
        this.shelfLife = shelfLife;
        this.description = description;
        this.note = note;
        this.deprecated = deprecated;
    }
}
