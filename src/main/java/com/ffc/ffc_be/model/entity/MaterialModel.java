package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.enums.UnitTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "materials")
@Entity
public class MaterialModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "code")
    private String code;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "shelf_life")
    private Integer shelfLife;

    @Column(name = "unit_type")
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum unitType;

    @Column(name = "description")
    private String description;
}
