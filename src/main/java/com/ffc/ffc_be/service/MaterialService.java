package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.MaterialCreateRequest;
import com.ffc.ffc_be.model.dto.request.MaterialUpdateRequest;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.model.entity.InventoryModel;
import com.ffc.ffc_be.model.entity.MaterialModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IInventoryRepository;
import com.ffc.ffc_be.repository.IMaterialRepository;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CustomLog
@RequiredArgsConstructor
public class MaterialService {
    private final IMaterialRepository materialRepository;
    private final IInventoryRepository inventoryRepository;
    private final UserCmsInfoService userCmsInfoService;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<MaterialResponse>> getDetail(Integer id) {
        try {
            MaterialModel material = materialRepository.findById(id).orElse(null);
            if (material == null) {
                return ResponseBuilder.badRequestResponse("Material not found, id:{}" + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            MaterialResponse response;
            try {
                response = mapper.map(material, MaterialResponse.class);
            } catch (Exception e) {
                log.error("Wrong type mapping when get material detail id {}", id, e);

                return ResponseBuilder.badRequestResponse("Error when get material detail, id: " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            return ResponseBuilder.okResponse("Get material detail successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Unexpected error when get material detail, id: " + id
                            + ". Might caused by query",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<List<MaterialResponse>>> getAll(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            Pageable pageable = PageRequest.of(page, size);

            Page<MaterialModel> result = materialRepository.findAll(pageable);
            List<MaterialResponse> response = result.stream()
                    .map(entity -> mapper.map(entity, MaterialResponse.class))
                    .collect(Collectors.toList());

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get list materials successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed when fetch data",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseDto<MaterialResponse>> create(MaterialCreateRequest request) {
        try {
            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            MaterialModel newMaterial = mapper.map(request, MaterialModel.class);
            newMaterial.setCreatedBy(userCmsInfoModel.getId());
            MaterialModel createdMaterial = materialRepository.save(newMaterial);
            MaterialResponse response = mapper.map(createdMaterial, MaterialResponse.class);

            try {
                InventoryModel newInventory = InventoryModel.builder()
                        .materialId(createdMaterial.getId())
                        .note("Auto created when add new materials")
                        .quantity(0)
                        .build();
                inventoryRepository.save(newInventory);
            } catch (Exception e) {
                log.error("Failed when create inventory after create new material", e);

                throw e;
            }

            return ResponseBuilder.okResponse("Created new material successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when create new material", e);

            return ResponseBuilder.badRequestResponse("Failed when create new material",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<MaterialResponse>> update(MaterialUpdateRequest request, Integer id) {
        try {
            UserCmsInfoModel userCmsInfo = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfo == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            MaterialModel updatingTarget = materialRepository.findById(id).orElse(null);
            if (updatingTarget == null) {
                return ResponseBuilder.badRequestResponse("Material update target not found, id: " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            mapper.map(request, updatingTarget);
            updatingTarget.setUpdatedBy(userCmsInfo.getId());
            MaterialModel updatedMaterial = materialRepository.save(updatingTarget);
            MaterialResponse response = mapper.map(updatedMaterial, MaterialResponse.class);

            return ResponseBuilder.okResponse("Update material successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when update material", e);

            return ResponseBuilder.badRequestResponse("Failed when update material, id: " + id,
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<Object>> delete(Integer id) {
        try {
            UserCmsInfoModel userCmsInfo = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfo == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            MaterialModel deletingTarget = materialRepository.findById(id).orElse(null);
            if (deletingTarget == null) {
                return ResponseBuilder.badRequestResponse("Material delete target not found, id: " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            deletingTarget.setUpdatedBy(userCmsInfo.getId());
            deletingTarget.setIsDeprecated(true);
            materialRepository.save(deletingTarget);

            return ResponseBuilder.okResponse("Delete material successfully!",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when delete material, id: {}", id, e);

            return ResponseBuilder.badRequestResponse("Delete material failed, id: " + id,
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
