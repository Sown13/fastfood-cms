package com.ffc.ffc_be.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_info")
@Entity
public class UserInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT", nullable = false)
    private int id;

    @Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(64)")
    private String password;

    @Column(name = "first_name", columnDefinition = "VARCHAR(255)")
    private String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(255)")
    private String lastName;

    @Column(name = "role", columnDefinition = "VARCHAR(100)")
    private String role;

    @Column(name = "supervisor", columnDefinition = "INT")
    private String supervisor;
}
