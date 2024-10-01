package com.ffc.ffc_be.controller.crud.cms;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.service.CRUDBaseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDBaseController<T, ID, RQ, RP> {
    private final CRUDBaseService<T, ID, RQ, RP> service;

    @Operation(summary = "Get detail by id",
            description = "Get detail by id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RP>> getById(@PathVariable ID id) {
        return service.findById(id);
    }

    @Operation(summary = "Get all",
            description = "Get all"
    )
    @GetMapping
    public ResponseEntity<ResponseDto<List<RP>>> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return service.findAll(page, size);
    }

    @Operation(summary = "Create new",
            description = "Create new"
    )
    @PostMapping
    public ResponseEntity<ResponseDto<RP>> create(@RequestBody RQ request) {
        return service.create(request);
    }

    @Operation(summary = "Update by id",
            description = "Update whole record by id"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<RP>> update(@PathVariable ID id, @RequestBody RQ request) {
        return service.update(request, id);
    }

    @Operation(summary = "Delete by id",
            description = "Delete by id (hard delete)"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> delete(@PathVariable ID id) {
        return service.delete(id);
    }
}
