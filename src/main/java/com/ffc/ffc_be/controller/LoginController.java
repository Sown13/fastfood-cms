package com.ffc.ffc_be.controller;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.LoginRequest;
import com.ffc.ffc_be.model.dto.response.LoginResponse;
import com.ffc.ffc_be.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ResponseDto<LoginResponse>> authenticate(@RequestBody LoginRequest request) {
        return authService.requestLogin(request);
    }
}
