package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.AccountBaseEntity;
import com.ffc.ffc_be.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    // tk doanh thu:511 tk nguon von:411 tk chi phi:641
    @Column(name = "account_number")
    private String accountNumber;
}
