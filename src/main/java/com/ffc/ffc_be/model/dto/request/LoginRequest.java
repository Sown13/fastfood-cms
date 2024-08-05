package com.ffc.ffc_be.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
