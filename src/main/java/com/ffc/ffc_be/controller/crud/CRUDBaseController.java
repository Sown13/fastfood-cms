package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.service.CRUDBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CRUDBaseController<T, ID, RQ, RP> {
    private final CRUDBaseService<T, ID, RQ, RP> service;
}
