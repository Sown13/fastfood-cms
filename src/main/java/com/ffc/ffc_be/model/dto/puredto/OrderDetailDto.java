package com.ffc.ffc_be.model.dto.puredto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {
    private Integer orderId;
    private Integer menuId;
    private String menuName;
    private Double menuPrice;
    private Integer cookTime;
    private Integer quantity;
    private String note;
}
