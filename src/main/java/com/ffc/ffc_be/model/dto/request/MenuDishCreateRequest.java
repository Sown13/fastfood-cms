package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDishCreateRequest {
    @Size(min = 1, max = 500)
    @NotBlank
    private String name;

    private String descriptionPublic;
    private String descriptionPrivate;

    @Min(1)
    private Double price;

    @Size(min = 1, max = 50)
    private String category;

    @Valid
    private List<MenuDishDetailDto> menuDishDetailList;

    private String image;
}
