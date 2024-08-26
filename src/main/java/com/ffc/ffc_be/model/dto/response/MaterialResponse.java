package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.Column;
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
public class MaterialResponse {
    private String name;
    private String code;
    private Integer shelfLife;

    @Enumerated(EnumType.STRING)
    private UnitTypeEnum unitType;

    private String description;
    private Integer createdBy;
}
