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
public class ImExRecipeResponse {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private String createdByName;
    private Integer responsibleBy;
    private String responsibleByName;
    private String description;
    private RepTypeEnum repType;
    private PurposeEnum purpose;
}
