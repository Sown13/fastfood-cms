package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.annotation.IsValidRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;

    @IsValidRole
    private String role;
}
