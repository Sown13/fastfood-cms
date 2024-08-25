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
@Table(name = "inventory")
@Entity
public class InventoryModel extends BaseEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "note")
    private String note;

    @Column(name = "quantity")
    private Integer quantity;
}
