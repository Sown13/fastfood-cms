package com.ffc.ffc_be.controller.auth;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check-authority")
@Tag(name = "[Check Authority]")
public class TestAuthorityController {
    @GetMapping("/accountant")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS')")
    public ResponseEntity<ResponseDto<Object>> checkAccountant() {
        return ResponseBuilder.okResponse("Ok, you have right to access accountant service", StatusCodeEnum.STATUSCODE1001);
    }

    @PreAuthorize("hasAnyRole('MARKETING', 'BOSS')")
    @GetMapping("/marketing")
    public ResponseEntity<ResponseDto<Object>> checkMarketing() {
        return ResponseBuilder.okResponse("Ok, you have right to access marketing service", StatusCodeEnum.STATUSCODE1001);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SALE', 'BOSS')")
    @GetMapping("/sale")
    public ResponseEntity<ResponseDto<Object>> checkSale() {
        return ResponseBuilder.okResponse("Ok, you have right to access sale service", StatusCodeEnum.STATUSCODE1001);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'BOSS')")
    @GetMapping("/manager")
    public ResponseEntity<ResponseDto<Object>> checkManager() {
        return ResponseBuilder.okResponse("Ok, you have right to access manager service", StatusCodeEnum.STATUSCODE1001);
    }

    @PreAuthorize("hasAnyRole('KITCHEN', 'BOSS')")
    @GetMapping("/kitchen")
    public ResponseEntity<ResponseDto<Object>> checkKitchen() {
        return ResponseBuilder.okResponse("Ok, you have right to access kitchen service", StatusCodeEnum.STATUSCODE1001);
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'SHIPPER', 'BOSS')")
    @GetMapping("/shipper")
    public ResponseEntity<ResponseDto<Object>> checkShipper() {
        return ResponseBuilder.okResponse("Ok, you have right to access shipper service", StatusCodeEnum.STATUSCODE1001);
    }

    @Operation(summary = "Check BOSS authority",
            description = "Check BOSS authority"
    )
    @PreAuthorize("hasRole('BOSS')")
    @GetMapping("/boss")
    public ResponseEntity<ResponseDto<Object>> checkBoss() {
        return ResponseBuilder.okResponse("Ok, you have right to access any service", StatusCodeEnum.STATUSCODE1001);
    }
}
