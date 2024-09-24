package com.ffc.ffc_be.model.dto.puredto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDishDetailDto {
    @NotNull
    @Min(0)
    private Integer materialId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @Size(max = 2000)
    private String note;

    @NotNull
    @Min(1)
    private Integer prepareTime;
}
