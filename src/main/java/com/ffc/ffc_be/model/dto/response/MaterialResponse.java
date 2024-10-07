package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.base.BaseResponse;
import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialResponse extends BaseResponse {
    private Integer id;
    private String name;
    private String code;
    private Integer shelfLife;

    @Enumerated(EnumType.STRING)
    private UnitTypeEnum unitType;

    private String description;
    private Integer createdBy;
    private Integer updatedBy;
    private String image;
}
