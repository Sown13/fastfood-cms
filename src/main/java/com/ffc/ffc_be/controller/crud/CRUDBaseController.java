package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.service.CRUDBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
public abstract class CRUDBaseController<T, ID, RQ, RP> {
    private final CRUDBaseService<T, ID, RQ, RP> service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RP>> getById(@PathVariable ID id) {
        return service.findById(id);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<RP>>> getAll() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<ResponseDto<RP>> create(@RequestBody RQ request) {
        return service.create(request);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<RP>> update(@PathVariable ID id, @RequestBody RQ request) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> delete(@PathVariable ID id) {
        return service.delete(id);
    }
}
