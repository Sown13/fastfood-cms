package com.ffc.ffc_be.model.dto.puredto;

import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExDetailHistoryDto {
    private Integer recipeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer materialId;
    private String materialName;
    private Integer quantity;
    private LocalDateTime factoryDate;
    private String note;
    private Double totalValue;
    private Double valuePerUnit;
    private String supplier;
    private Integer createdBy;
    private Integer responsibleBy;
    private RepTypeEnum repType;
    private PurposeEnum purpose;
}
