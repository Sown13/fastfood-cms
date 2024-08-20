package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.enums.TimeUnitEnum;
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
@Table(name = "salaries")
@Entity
public class SalaryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    private int salary;

    @Column(name = "time_unit")
    @Enumerated(EnumType.STRING)
    private TimeUnitEnum timeUnit;
}
