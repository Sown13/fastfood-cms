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
@Table(name = "order")
@Entity
public class OrderModel extends BaseEntity {
    @Column(name = "description")
    private String description;

    @Column(name = "promotion_code")
    private String promotionCode;
}
