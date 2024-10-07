package com.ffc.ffc_be.model.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AccountBaseEntity extends BaseEntity{
    @Column(name = "deleted")
    private Boolean isDeleted;

    @Column(name = "account_name")
    private String name;

    @Column(name = "created_by")
    private Integer createdBy;
}
