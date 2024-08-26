package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.service.CRUDBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
public abstract class CRUDBaseController<T, ID, RQ, RP> {
    private final CRUDBaseService<T, ID, RQ, RP> service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<RP>> getById(@PathVariable ID id) {
        return service.findById(id);
    }
}
