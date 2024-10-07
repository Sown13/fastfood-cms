package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.enums.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private String username;
    private String fullName;
    private String primaryPhone;
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private Integer supervisorId;
    private Integer createdBy;
    private String salaryId;
    private String description;
}
