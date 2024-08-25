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
@Table(name = "inventory_history_detail")
@Entity
public class InventoryHistoryDetailModel extends BaseEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "inv_history_id")
    private Integer inventoryHistoryId;

    @Column(name = "note")
    private String note;

    @Column(name = "quantity")
    private Integer quantity;
}
