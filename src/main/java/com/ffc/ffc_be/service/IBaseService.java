package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBaseService <T, ID, RQ, RP> {
    ResponseEntity<ResponseDto<RP>> findById(ID id);
}
