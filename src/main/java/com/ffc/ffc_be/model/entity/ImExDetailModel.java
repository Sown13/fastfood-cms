package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.QueueStatus;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "import_export_detail")
@Entity
public class ImExDetailModel extends BaseEntity {
    @Column(name = "recipe_id")
    private Integer recipeId;

    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "factory_date")
    private LocalDateTime factoryDate;

    @Column(name = "note")
    private String note;

    @Column(name = "total_value")
    private Double totalValue;

    @Column(name = "unit_value")
    private Double valuePerUnit;

    @Column(name = "supplier") // supplier if it is import and buyer if it is export
    private String supplier;

    @Column(name = "queue_status")
    @Enumerated(EnumType.STRING)
    private QueueStatus queueStatus;

    @Column(name = "quantity_left_in_queue")
    private Integer quantityLeftInQueue;

    @Column(name = "import_detail_id")
    private Integer importDetailId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RepTypeEnum type;
}
