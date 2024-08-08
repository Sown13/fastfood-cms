package com.ffc.ffc_be.controller;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.RegisterRequest;
import com.ffc.ffc_be.model.entity.UserInfoModel;
import com.ffc.ffc_be.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('BOSS', 'MANAGER')")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @Operation(summary = "Only BOSS and MANAGER can create new user",
            description = "Only BOSS and MANAGER can create new user, MANAGER can only create SALE/SHIPPER"
    )
    @PostMapping("/create-user")
    public ResponseEntity<ResponseDto<UserInfoModel>> register(@RequestBody @Valid RegisterRequest request) {
        return userInfoService.register(request);
    }
}