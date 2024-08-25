package com.ffc.ffc_be.model.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class AccountBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "account_name")
    private String name;

    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "created_by")
    private Integer createdBy;
}
