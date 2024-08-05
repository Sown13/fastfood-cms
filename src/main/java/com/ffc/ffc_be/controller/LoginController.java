package com.ffc.ffc_be.controller;

import com.ffc.ffc_be.config.security.UserDetailsImpl;
import com.ffc.ffc_be.model.dto.request.LoginRequest;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.dto.response.LoginResponse;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.service.security.AuthenticationService;
import com.ffc.ffc_be.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfoModel> register(@RequestBody RegisterRequest request) {
        UserDetailsImpl registeredUser = authenticationService.signup(request);

        return ResponseEntity.ok(registeredUser.getUser());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        UserDetailsImpl authenticatedUser = authenticationService.authenticate(request);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
