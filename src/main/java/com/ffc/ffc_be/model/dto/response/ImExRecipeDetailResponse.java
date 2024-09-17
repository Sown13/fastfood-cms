package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.dto.puredto.ImExRecipeDetailDto;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
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
public class ImExRecipeDetailResponse {
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createdBy;
    private String createdByName;
    private Integer responsibleBy;
    private String responsibleName;
    private String description;
    private RepTypeEnum repType;
    private PurposeEnum purpose;
    private List<ImExRecipeDetailDto> imExDetailList;
}
