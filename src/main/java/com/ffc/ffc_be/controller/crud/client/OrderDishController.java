package com.ffc.ffc_be.controller.crud.client;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.OrderCreateRequest;
import com.ffc.ffc_be.model.dto.response.OrderDetailResponse;
import com.ffc.ffc_be.model.entity.OrderModel;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import com.ffc.ffc_be.service.OrderDishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-dish")
@RequiredArgsConstructor
public class OrderDishController {
    private final OrderDishService orderService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<OrderModel>>> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<OrderDetailResponse>> getOrderById(@PathVariable Integer id) {
        return orderService.getOrderDetailById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Object>> createOrder(@RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> deleteOrder(@PathVariable Integer id) {
        return orderService.deleteOrder(id);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<ResponseDto<Object>> updateOrderStatus(@PathVariable Integer id,
                                                                 @RequestBody OrderStatusEnum status) {
        return orderService.updateOrderStatus(id, status);
    }
}
