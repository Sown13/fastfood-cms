package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "import_export_rep")
@Entity
public class ImExRecipeModel extends BaseEntity {
    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "responsible_by")
    private Integer responsibleBy;

    @Column(name = "description")
    private String description;

    @Column(name = "rep_type")
    @Enumerated(EnumType.STRING)
    private RepTypeEnum repType;

    @Column(name = "purpose")
    @Enumerated(EnumType.STRING)
    private PurposeEnum purpose;
}
