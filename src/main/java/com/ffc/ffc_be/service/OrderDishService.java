package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.puredto.ImExDetailDto;
import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailMaterialDto;
import com.ffc.ffc_be.model.dto.puredto.OrderDetailCreateDto;
import com.ffc.ffc_be.model.dto.puredto.OrderDetailDto;
import com.ffc.ffc_be.model.dto.request.ImExRecipeCreateRequest;
import com.ffc.ffc_be.model.dto.request.OrderCreateRequest;
import com.ffc.ffc_be.model.dto.response.OrderDetailResponse;
import com.ffc.ffc_be.model.entity.OrderDetailModel;
import com.ffc.ffc_be.model.entity.OrderModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IMenuDishDetailRepository;
import com.ffc.ffc_be.repository.IOderDetailRepository;
import com.ffc.ffc_be.repository.IOrderRepository;
import com.ffc.ffc_be.transaction.ImExTransaction;
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
    private final ImExTransaction imExTransaction;
    private final IMenuDishDetailRepository menuDishDetailRepository;
    // todo need refactor this service later

    public ResponseEntity<ResponseDto<List<OrderModel>>> getAllOrders(OrderStatusEnum status) {
        List<OrderModel> response;
        if (status == null) {
            response = orderRepository.findAll();
        } else {
            response = orderRepository.findAllByStatus(status);
        }

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
                    .orderByName(orderModel.getOrderByName())
                    .address(orderModel.getAddress())
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
//            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
//            if (userCmsInfoModel == null) {
//                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
//                        StatusCodeEnum.STATUSCODE2001);
//            }

            OrderModel newOrder = OrderModel.builder()
                    .address(request.getAddress())
                    .orderByName(request.getFullName())
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
            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            OrderModel orderModel = orderRepository.findById(id).orElse(null);
            if (orderModel == null) {
                return ResponseBuilder.badRequestResponse("Order not found!", StatusCodeEnum.STATUSCODE2001);
            }

            if (isValidateOrderStatusUpdateRequest(orderModel, status)) {
                if (status.equals(OrderStatusEnum.COMPLETED)) {
                    String message = updateInventoryAfterCompleteOrder(id, userCmsInfoModel.getId(), orderModel);

                    if (!message.equals("success")) {
                        return ResponseBuilder.badRequestResponse(message, StatusCodeEnum.STATUSCODE2001);
                    }
                }

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

    private String updateInventoryAfterCompleteOrder(Integer orderId, Integer userId, OrderModel order) {
        try {
            List<OrderDetailDto> orderDetailList = orderRepository.findOrderDetailListByOrderId(orderId);

            List<MenuDishDetailMaterialDto> materialList = new ArrayList<>();
            List<Integer> quantity = new ArrayList<>();
            for (OrderDetailDto detailDto : orderDetailList) {
                List<MenuDishDetailMaterialDto> result = menuDishDetailRepository.findRecipeMaterialByMenuId(detailDto.getMenuId());
                for (int i = 0; i < result.size(); i++) {
                    quantity.add(detailDto.getQuantity());
                }

                materialList.addAll(result);
            }

            ImExRecipeCreateRequest request = ImExRecipeCreateRequest.builder()
                    .responsibleBy(userId)
                    .description("Use for order dish (bussiness)")
                    .repType(RepTypeEnum.EXPORT)
                    .purpose(PurposeEnum.BUSINESS)
                    .supplier(order.getOrderByName())
                    .build();

            List<ImExDetailDto> listImExDetail = new ArrayList<>();
            for (int i = 0; i < materialList.size(); i++) {
                ImExDetailDto imExDetailDto = ImExDetailDto.builder()
                        .materialId(materialList.get(i).getMaterialId())
                        .quantity(materialList.get(i).getQuantity() * quantity.get(i))
                        .note("User for cooking for order " + orderId)
                        .totalValue(1d)//todo need to calculate original price for this
                        .build();
                listImExDetail.add(imExDetailDto);
            }

            request.setDetailList(listImExDetail);

            try {
                imExTransaction.createNewImExRecipe(request);
            } catch (Exception e) {
                return "Not enough material to cook this dish";
            }

            return "success";
        } catch (Exception e) {
            return "Error when update order";
        }
    }
}
