package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.TimeUnitEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "salaries")
@Entity
public class SalaryModel extends BaseEntity {
    @Column(name = "salary")//count by VND
    private Double salary;

    @Column(name = "time_unit")
    @Enumerated(EnumType.STRING)
    private TimeUnitEnum timeUnit;
}
