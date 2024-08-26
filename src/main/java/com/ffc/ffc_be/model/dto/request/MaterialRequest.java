package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequest {
    private String name;
    private String code;
    private Integer shelfLife;

    @Enumerated(EnumType.STRING)
    private UnitTypeEnum unitType;

    private String description;
}
