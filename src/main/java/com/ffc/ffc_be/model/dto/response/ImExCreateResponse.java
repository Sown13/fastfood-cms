package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.dto.puredto.ImExRecipeDto;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExCreateResponse {
    private RepTypeEnum repType;
    private PurposeEnum purpose;
    private String description;
    private List<ImExRecipeDto> imExRecipes;
}
