package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.model.dto.puredto.OrderDetailCreateDto;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private String description;
    private String promotionCode;
    private OrderStatusEnum status;

    @Valid
    private List<OrderDetailCreateDto> detailList;

    private String fullName;
    private String address;
}
