package com.ffc.ffc_be.model.dto.puredto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExRecipeDetailDto {
    private Integer id;
    private Integer materialId;
    private String materialName;
    private Integer quantity;
    private LocalDateTime factoryDate;
    private String note;
    private Double totalValue;
    private Double valuePerUnit;
    private String supplier;
}
