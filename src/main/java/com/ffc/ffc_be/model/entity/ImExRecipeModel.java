package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "import_export_rep")
@Entity
public class ImExRecipeModel extends BaseEntity {
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "responsible_by")
    private Integer responsibleBy;

    @Column(name = "description")
    private String description;

    @Column(name = "rep_type")
    @Enumerated
    private RepTypeEnum repType;

    @Column(name = "purpose")
    @Enumerated
    private PurposeEnum purpose;
}