package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory_history")
@Entity
public class InventoryHistoryModel extends BaseEntity {
    @Column(name = "reported_date")
    private LocalDate reportedDate;

    @Column(name="reported_by")
    private Integer reportedBy;
}
