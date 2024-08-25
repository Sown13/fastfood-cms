package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_detail")
@Entity
public class OrderDetailModel extends BaseEntity {
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "menu_id")
    private Integer menuId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;
}
