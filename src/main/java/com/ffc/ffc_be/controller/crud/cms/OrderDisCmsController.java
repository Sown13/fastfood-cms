package com.ffc.ffc_be.controller.crud.cms;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import com.ffc.ffc_be.service.OrderDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cms/order-dish")
@RequiredArgsConstructor
public class OrderDisCmsController {
    private final OrderDishService orderService;

    @PostMapping("/{id}/status")
    public ResponseEntity<ResponseDto<Object>> updateOrderStatus(@PathVariable Integer id,
                                                                 @RequestBody OrderStatusEnum status) {
        return orderService.updateOrderStatus(id, status);
    }
}
