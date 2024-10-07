package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.AccountBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_asset")
@Entity
public class AccountAssetModel extends AccountBaseEntity {
    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    // tk tai san:111
    @Column(name = "account_number")
    private String accountNumber;
}
