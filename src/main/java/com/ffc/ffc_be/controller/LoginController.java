package com.ffc.ffc_be.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<ResponseDto<LoginResponse>> authenticate(@RequestBody LoginRequest request) {
        UserDetailsImpl authenticatedUser;
        try {
            authenticatedUser = authenticationService.authenticate(request);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed",StatusCodeEnum.STATUSCODE002);
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseBuilder.okResponse("Login successfull", loginResponse, StatusCodeEnum.STATUSCODE001);
    }
}
