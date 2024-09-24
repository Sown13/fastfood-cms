package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.ImExRecipeCreateRequest;
import com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse;
import com.ffc.ffc_be.model.dto.response.ImExRecipeDetailResponse;
import com.ffc.ffc_be.model.dto.response.ImExRecipeResponse;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import com.ffc.ffc_be.service.ImExRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/im-ex-recipe")
@RequiredArgsConstructor
public class ImExRecipeController {
    private final ImExRecipeService imExRecipeService;

    @Operation(summary = "Get list im/ex recipe list",
            description = "Default order by newest, default page = 0, size = 10"
    )
    @GetMapping
    public ResponseEntity<ResponseDto<List<ImExRecipeResponse>>> getImExRecipeList(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                                                   @RequestParam(name = "repType", defaultValue = "IMPORT") RepTypeEnum repType) {
        return imExRecipeService.getImExRecipeList(page, size, repType);
    }

    @Operation(summary = "Get list im/ex detail",
            description = "Default order by newest, default page = 0, size = 10"
    )
    @GetMapping("/detail-list")
    public ResponseEntity<ResponseDto<List<ImExDetailHistoryResponse>>> getImExDetailHistoryList(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                                                                 @RequestParam(name = "repType", defaultValue = "IMPORT") RepTypeEnum repType) {
        return imExRecipeService.getImExDetailHistoryList(page, size, repType);
    }

    @Operation(summary = "Create new import or export recipe",
            description = "repType = IMPORT or EXPORT"
    )
    @PostMapping
    public ResponseEntity<ResponseDto<Object>> createImExRecipe(@RequestBody @Valid ImExRecipeCreateRequest request) {
        return imExRecipeService.createImEx(request);
    }

    @Operation(summary = "Get list im/ex recipe detail",
            description = "Default order by newest, default page = 0, size = 10"
    )
    @GetMapping("/{recipeId}")
    public ResponseEntity<ResponseDto<ImExRecipeDetailResponse>> getImExRecipeDetail(@PathVariable Integer recipeId) {
        return imExRecipeService.getImExRecipeDetail(recipeId);
    }
}
