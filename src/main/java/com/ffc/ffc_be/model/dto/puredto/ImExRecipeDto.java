package com.ffc.ffc_be.model.dto.puredto;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExRecipeDto {
    private Integer id;
    private String name;
    private String code;
    private Integer quantity;
    private UnitTypeEnum unitType;
    private String note;
    private Double totalValue;
    private Double valuePerUnit;
    private String supplier;
}
