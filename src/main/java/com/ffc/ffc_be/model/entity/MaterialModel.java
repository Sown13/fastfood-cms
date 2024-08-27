package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "materials")
@Entity
public class MaterialModel extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @Column(name = "unit_type")
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum unitType;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "deprecated")
    private Boolean isDeprecated;
}
