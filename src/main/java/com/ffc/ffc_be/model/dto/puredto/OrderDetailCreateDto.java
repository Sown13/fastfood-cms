package com.ffc.ffc_be.model.dto.puredto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailCreateDto {
    private Integer menuId;
    private Integer quantity;
    private String note;
}
