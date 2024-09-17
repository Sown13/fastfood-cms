package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExDetailHistoryResponse {
    private Integer id;
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
    private String createdByName;
    private Integer responsibleBy;
    private String responsibleName;
    private RepTypeEnum repType;
    private PurposeEnum purpose;
}
