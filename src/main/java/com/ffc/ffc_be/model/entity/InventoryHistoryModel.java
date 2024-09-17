package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "inventory_history")
@Entity
public class InventoryHistoryModel extends BaseEntity {
    @Column(name = "reported_date")
    private LocalDateTime reportedDate;

    @Column(name="reported_by")
    private Integer reportedBy;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
