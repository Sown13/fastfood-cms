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
@Builder
@Table(name = "account_asset")
@Entity
public class AccountAssetModel extends AccountBaseEntity {
}
