package com.ffc.ffc_be.model.entity;

import com.ffc.ffc_be.model.base.BaseEntity;
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
@Table(name = "user_cms_info")
@Entity
public class UserCmsInfoModel extends BaseEntity {
    @Column(name = "username", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(64)")
    private String password;

    @Column(name = "fullname", columnDefinition = "VARCHAR(255)")
    private String fullName;

    @Column(name = "phone", columnDefinition = "VARCHAR(13)")
    private String primaryPhone;

    @Column(name = "email", columnDefinition = "VARCHAR(128)")
    private String email;

    @Column(name = "role", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Column(name = "supervisor_id", columnDefinition = "INT")
    private Integer supervisorId;

    @Column(name = "created_by", columnDefinition = "INT")
    private Integer createdBy;

    @Column(name = "salary_id")
    private String salaryId;

    @Column(name = "description")
    private String description;
}
