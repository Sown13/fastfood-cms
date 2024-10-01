package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.puredto.OrderDetailCreateDto;
import com.ffc.ffc_be.model.dto.puredto.OrderDetailDto;
import com.ffc.ffc_be.model.dto.request.OrderCreateRequest;
import com.ffc.ffc_be.model.dto.response.OrderDetailResponse;
import com.ffc.ffc_be.model.entity.OrderDetailModel;
import com.ffc.ffc_be.model.entity.OrderModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IOderDetailRepository;
import com.ffc.ffc_be.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDishService {
    private final IOrderRepository orderRepository;
    private final IOderDetailRepository orderDetailRepository;
    private final UserCmsInfoService userCmsInfoService;
    // todo need refactor this service later

    public ResponseEntity<ResponseDto<List<OrderModel>>> getAllOrders() {
        List<OrderModel> response = orderRepository.findAll();
        return ResponseBuilder.okResponse("Get order list successfully",
                response,
                StatusCodeEnum.STATUSCODE1001);
    }

    public ResponseEntity<ResponseDto<OrderDetailResponse>> getOrderDetailById(Integer id) {
        try {
            List<OrderDetailDto> orderDetailList = orderRepository.findOrderDetailListByOrderId(id);
            OrderModel orderModel = orderRepository.findById(id).orElse(null);

            if (orderModel == null) {
                ResponseBuilder.badRequestResponse("Get order detail failed when fetch order data",
                        StatusCodeEnum.STATUSCODE2001);
            }

            double totalPrice = orderDetailList.stream()
                    .mapToDouble(OrderDetailDto::getMenuPrice)
                    .sum();

            OrderDetailResponse response = OrderDetailResponse.builder()
                    .description(orderModel.getDescription())
                    .promotionCode(orderModel.getPromotionCode())
                    .status(orderModel.getStatus())
                    .orderBy(orderModel.getOrderBy())
                    .orderByName(orderModel.getOrderByName())
                    .totalPrice(totalPrice)
                    .orderDetaiList(orderDetailList)
                    .build();

            return ResponseBuilder.okResponse("Get order detail successfully",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Get order detail failed, error",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<Object>> createOrder(OrderCreateRequest request) {
        try {
            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            OrderModel newOrder = OrderModel.builder()
                    .orderBy(userCmsInfoModel.getId())
                    .orderByName(userCmsInfoModel.getFullName())
                    .description(request.getDescription())
                    .promotionCode(request.getPromotionCode())
                    .status(request.getStatus())
                    .build();

            newOrder = orderRepository.save(newOrder);

            List<OrderDetailModel> detailList = new ArrayList<>();
            for (OrderDetailCreateDto dto : request.getDetailList()) {
                OrderDetailModel model = OrderDetailModel.builder()
                        .orderId(newOrder.getId())
                        .menuId(dto.getMenuId())
                        .quantity(dto.getQuantity())
                        .note(dto.getNote())
                        .build();
                detailList.add(model);
            }

            orderDetailRepository.saveAll(detailList);

            return ResponseBuilder.okResponse("Create order successfully",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Create order dish failed", StatusCodeEnum.STATUSCODE2001);
        }
    }

    public OrderModel updateOrder(Integer id, OrderModel orderDetails) {
        return null;
    }

    public ResponseEntity<ResponseDto<Object>> deleteOrder(Integer id) {
        try {
            orderRepository.deleteById(id);

            return ResponseBuilder.okResponse("Delete successfully", StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Delete failed, error", StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<Object>> updateOrderStatus(Integer id, OrderStatusEnum status) {
        try {
            OrderModel orderModel = orderRepository.findById(id).orElse(null);
            if (orderModel == null) {
                return ResponseBuilder.badRequestResponse("Order not found!", StatusCodeEnum.STATUSCODE2001);
            }

            if (isValidateOrderStatusUpdateRequest(orderModel, status)) {
                orderModel.setStatus(status);
                orderRepository.save(orderModel);

                return ResponseBuilder.okResponse("Update order successfully", StatusCodeEnum.STATUSCODE1001);
            }

            return ResponseBuilder.badRequestResponse("Update failed, order already solve - not allow to update", StatusCodeEnum.STATUSCODE2001);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Update failed, error", StatusCodeEnum.STATUSCODE2001);

        }

    }

    private boolean isValidateOrderStatusUpdateRequest(OrderModel model, OrderStatusEnum orderStatusEnum) {
        if (model.getStatus() != OrderStatusEnum.WAITING) {
            return false; // not allow order that solved to be updated
        }

        return true;
    }
}