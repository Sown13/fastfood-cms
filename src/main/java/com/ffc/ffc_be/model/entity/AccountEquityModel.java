package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.AccountBaseEntity;
import com.ffc.ffc_be.model.base.BaseEntity;
import com.ffc.ffc_be.model.enums.AccountCalculateType;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_equity")
@Entity
public class AccountEquityModel extends AccountBaseEntity {
    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    // tk nguon von:411  tk doanh thu:511  tk chi phi:641
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "calculate_type")
    @Enumerated(EnumType.STRING)
    private AccountCalculateType calculateType;
}
