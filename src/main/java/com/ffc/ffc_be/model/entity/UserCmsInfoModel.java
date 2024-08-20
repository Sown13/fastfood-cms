package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.enums.RoleEnum;
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
@Table(name = "user_info")
@Entity
public class UserCmsInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "INT", nullable = false)
    private int id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(64)")
    private String password;

    @Column(name = "fullname", columnDefinition = "VARCHAR(255)")
    private String firstName;

    @Column(name = "phone", columnDefinition = "VARCHAR(13)")
    private String phone;

    @Column(name = "phone", columnDefinition = "VARCHAR(128)")
    private String email;

    @Column(name = "role", columnDefinition = "VARCHAR(50)")
    private RoleEnum role;

    @Column(name = "supervisor_id", columnDefinition = "INT")
    private String supervisorId;

    @Column(name = "salary_id")
    private String salaryId;

    @Column(name = "description")
    private String description;
}