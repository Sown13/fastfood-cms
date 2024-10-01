package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_dish")
@Entity
public class OrderModel extends BaseEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "promotion_code")
    private String promotionCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @Column(name = "order_by")
    private Integer orderBy;

    @Column(name = "order_by_name")
    private String orderByName;
}
