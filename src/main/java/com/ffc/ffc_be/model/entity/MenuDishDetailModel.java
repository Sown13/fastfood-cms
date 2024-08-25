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
@Table(name = "menu_dish_detail")
@Entity
public class MenuDishDetailModel extends BaseEntity {
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "menu_dish_id")
    private Integer menuDishId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "note")
    private String note;

    @Column(name = "prepare_time")
    private Integer prepareTime;
}
