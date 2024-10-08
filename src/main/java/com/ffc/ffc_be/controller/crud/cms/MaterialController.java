package com.ffc.ffc_be.controller.crud.cms;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.MaterialCreateRequest;
import com.ffc.ffc_be.model.dto.request.MaterialUpdateRequest;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialController {
    private final MaterialService materialService;

    @Operation(summary = "Get detail by id",
            description = "Get detail by id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS', 'MANAGER')")
    public ResponseEntity<ResponseDto<MaterialResponse>> getById(@PathVariable Integer id) {
        return materialService.getDetail(id);
    }

    @Operation(summary = "Get all by page, start from page 0",
            description = "Get all, get metaData in response, page start from 0, default page =0, size = 10"
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS', 'MANAGER')")
    public ResponseEntity<ResponseDto<List<MaterialResponse>>> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return materialService.getAll(page, size);
    }

    @Operation(summary = "Create new",
            description = "Create new"
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS')")
    public ResponseEntity<ResponseDto<MaterialResponse>> create(@RequestBody MaterialCreateRequest request) {
        return materialService.create(request);
    }

    @Operation(summary = "Update material by id",
            description = "Update material by id (update whole material), need send both old and new info to update"
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS')")
    public ResponseEntity<ResponseDto<MaterialResponse>> update(@PathVariable Integer id, @RequestBody MaterialUpdateRequest request) {
        return materialService.update(request, id);
    }

    @Operation(summary = "Delete material by id, set deprecated = true instead of hard delete",
            description = "Delete material by id, set deprecated = true instead of hard delete"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ACCOUNTANT', 'BOSS')")
    public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Integer id) {
        return materialService.delete(id);
    }
}
