package com.ffc.ffc_be.model.dto.puredto;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDishDetailMaterialDto {
    private Integer materialId;
    private String materialName;
    private Integer quantity;
    private UnitTypeEnum unitType;
    private String note;
    private Integer prepareTime;
}
