package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.service.ImExRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/im-ex-recipe")
@RequiredArgsConstructor
public class ImExRecipeController {
    private final ImExRecipeService imExRecipeService;

    @Operation(summary = "Create new import or export recipe",
            description = "repType = IMPORT or EXPORT"
    )
    @PostMapping("/im-ex")
    public ResponseEntity<ResponseDto<Object>> demoImEx(@RequestBody @Valid ImExCreateRequest request){
        return imExRecipeService.createImEx(request);
    }
}
