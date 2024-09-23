package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailMaterialDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDishRecipeResponse {
    private Integer menuDishId;
    private String menuDishName;
    private String descriptionPublic;
    private String descriptionPrivate;
    private Integer createdBy;
    private Integer cookTime;
    private Double price;
    private String category;
    private List<MenuDishDetailMaterialDto> materialList;
}
