package com.ffc.ffc_be.controller;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.security.AuthenticationService;
import com.ffc.ffc_be.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('BOSS', 'MANAGER')")
public class UserInfoController {
    private final AuthenticationService authenticationService;

    @PostMapping("/create-user")
    public ResponseEntity<ResponseDto<UserInfoModel>> register(@RequestBody @Valid RegisterRequest request) {
        UserDetailsImpl registeredUser;
        try {
            registeredUser = authenticationService.signup(request);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed", StatusCodeEnum.STATUSCODE2001);
        }

        return ResponseBuilder.okResponse("Create new user successfully", registeredUser.getUser(), StatusCodeEnum.STATUSCODE1001);
    }
}