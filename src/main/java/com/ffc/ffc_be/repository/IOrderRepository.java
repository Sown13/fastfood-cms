package com.ffc.ffc_be.repository;

import com.ffc.ffc_be.model.dto.puredto.OrderDetailDto;
import com.ffc.ffc_be.model.entity.OrderModel;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel, Integer> {

    @Query(value = "SELECT new com.ffc.ffc_be.model.dto.puredto.OrderDetailDto(" +
            "odm.orderId, odm.menuId, md.name, md.price ," +
            "md.cookTime, odm.quantity, odm.note )" +
            "FROM OrderDetailModel odm " +
            "JOIN MenuDishModel md ON odm.menuId = md.id " +
            "WHERE odm.orderId = :orderId")
    List<OrderDetailDto> findOrderDetailListByOrderId(Integer orderId);

    List<OrderModel> findAllByStatus(OrderStatusEnum status, Sort sort);
}
