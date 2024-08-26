package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.MaterialRequest;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.model.entity.MaterialModel;
import com.ffc.ffc_be.service.CRUDBaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materials")
public class MaterialController extends CRUDBaseController<MaterialModel, Integer, MaterialRequest, MaterialResponse>{
    public MaterialController(CRUDBaseService<MaterialModel, Integer, MaterialRequest, MaterialResponse> service) {
        super(service);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<MaterialResponse>> getById(@PathVariable Integer id) {
        return super.getById(id);
    }

    @Override
    public ResponseEntity<ResponseDto<List<MaterialResponse>>> getAll() {
        return super.getAll();
    }

    @Override
    public ResponseEntity<ResponseDto<MaterialResponse>> create(@RequestBody MaterialRequest request) {
        return super.create(request);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto<MaterialResponse>> update(@PathVariable Integer id, @RequestBody MaterialRequest request) {
        return super.update(id, request);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Object>> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}
