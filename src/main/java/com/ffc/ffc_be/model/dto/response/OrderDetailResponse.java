package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.dto.puredto.OrderDetailDto;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String description;
    private String promotionCode;
    private OrderStatusEnum status;
    private Integer orderBy;
    private String orderByName;
    private List<OrderDetailDto> orderDetaiList;
    private Double totalPrice;
}
