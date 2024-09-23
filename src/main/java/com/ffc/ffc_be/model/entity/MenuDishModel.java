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
@Table(name = "menu_dish")
@Entity
public class MenuDishModel extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description_public")
    private String descriptionPublic;

    @Column(name = "description_prive")
    private String descriptionPrivate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "cook_time")//Count by minutes
    private Integer cookTime;

    @Column(name = "price")//Count by VND
    private Double price;

    @Column(name = "category")//manual add
    private String category;
}
