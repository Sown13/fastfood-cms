package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.MaterialRequest;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.model.entity.MaterialModel;
import com.ffc.ffc_be.service.CRUDBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
