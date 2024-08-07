package com.ffc.ffc_be.controller;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.security.AuthenticationService;
import com.ffc.ffc_be.security.JwtService;
import com.ffc.ffc_be.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
public class UserInfoController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/create-user")
    public ResponseEntity<ResponseDto<UserInfoModel>> register(@RequestBody RegisterRequest request) {
        UserDetailsImpl registeredUser;
        try {
            registeredUser = authenticationService.signup(request);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed", StatusCodeEnum.STATUSCODE002);
        }

        return ResponseBuilder.okResponse("Create new user successfully", registeredUser.getUser(), StatusCodeEnum.STATUSCODE001);
    }

    @GetMapping("/get-employee")
    public ResponseEntity<ResponseDto<UserInfoModel>> getEmployee() {
        return ResponseBuilder.okResponse("Ok", StatusCodeEnum.STATUSCODE002);
    }
}