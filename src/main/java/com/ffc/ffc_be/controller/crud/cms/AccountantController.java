package com.ffc.ffc_be.controller.crud.cms;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.AccountantReportResponse;
import com.ffc.ffc_be.service.AccountantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accountant")
public class AccountantController {
    private final AccountantService accountantService;

    @GetMapping("/test")
    public ResponseEntity<ResponseDto<AccountantReportResponse>> getBusinessReport() {
        return accountantService.getAccountantReport();
    }
}
