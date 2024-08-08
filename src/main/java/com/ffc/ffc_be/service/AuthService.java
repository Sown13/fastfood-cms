package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.LoginRequest;
import com.ffc.ffc_be.model.dto.response.LoginResponse;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.security.AuthenticationService;
import com.ffc.ffc_be.security.JwtService;
import com.ffc.ffc_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public ResponseEntity<ResponseDto<LoginResponse>> requestLogin(LoginRequest request) {
        UserDetailsImpl authenticatedUser;
        try {
            authenticatedUser = authenticationService.authenticate(request);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Login Failed", StatusCodeEnum.STATUSCODE2001);
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseBuilder.okResponse("Login successfully", loginResponse, StatusCodeEnum.STATUSCODE1001);
    }
}
