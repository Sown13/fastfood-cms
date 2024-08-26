package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.dto.request.MaterialRequest;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.model.entity.MaterialModel;
import com.ffc.ffc_be.repository.IMaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MaterialService extends CRUDBaseService<MaterialModel, Integer, MaterialRequest, MaterialResponse> {
    public MaterialService(IMaterialRepository repository, ModelMapper mapper, UserCmsInfoService userCmsInfoService) {
        super(repository, mapper, userCmsInfoService, MaterialModel.class, MaterialRequest.class, MaterialResponse.class);
    }
}
