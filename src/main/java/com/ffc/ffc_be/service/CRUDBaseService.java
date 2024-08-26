package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import jakarta.validation.Valid;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@CustomLog
@RequiredArgsConstructor
public abstract class CRUDBaseService<T, ID, RQ, RP> {
    private final JpaRepository<T, ID> repository;
    private final ModelMapper mapper;
    private final UserCmsInfoService userCmsInfoService;
    private final Class<T> typeOfModel;
    private final Class<RQ> typeOfRequest;
    private final Class<RP> typeOfResponse;

    public ResponseEntity<ResponseDto<RP>> findById(@Valid ID id) {
        try {
            T result = repository.findById(id).orElse(null);
            if (result == null) {
                return ResponseBuilder.badRequestResponse("Cannot find the record with ID " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }
            RP response = mapper.map(result, typeOfResponse);

            return ResponseBuilder.okResponse("Successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception enemyOfEternity) {
            log.error("Error when fetching data from db, id:{}", id, enemyOfEternity);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<List<RP>>> findAll() {
        try {
            List<T> result = repository.findAll();
            List<RP> response = new ArrayList<>();
            for (T entity : result) {
                RP item = mapper.map(entity, typeOfResponse);
                response.add(item);
            }

            return ResponseBuilder.okResponse("Successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception enemyOfEternity) {
            log.error("Error when fetching data from db", enemyOfEternity);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<RP>> create(RQ request) {
        try {
            T newRecord = mapper.map(request, typeOfModel);
            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            try {
                newRecord.getClass().getMethod("setCreatedBy", Integer.class).invoke(newRecord, userCmsInfoModel.getId());
            } catch (Exception e) {
                log.info("There no created_by for this entity", e);
            }

            T result = repository.save(newRecord);
            RP response = mapper.map(result, typeOfResponse);

            if (response == null) {
                return ResponseBuilder.badRequestResponse("Request Failed!",
                        StatusCodeEnum.STATUSCODE2001);
            }

            return ResponseBuilder.okResponse("Successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when save/map model for {} {}", typeOfModel.toString(), typeOfResponse.toString(), e);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<RP>> update(RQ request, ID id) {
        try {
            T updatingTarget = repository.findById(id).orElse(null);
            if (updatingTarget == null) {
                return ResponseBuilder.badRequestResponse("Cannot find the record with ID " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            UserCmsInfoModel userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                return ResponseBuilder.badRequestResponse("Request Failed! Cannot get user info from context",
                        StatusCodeEnum.STATUSCODE2001);
            }

            try {
                updatingTarget.getClass().getMethod("setUpdatedBy", Integer.class).invoke(updatingTarget, userCmsInfoModel.getId());
            } catch (Exception e) {
                log.info("There no updated_by for this entity", e);
            }

            mapper.map(request, updatingTarget);

            T updatedEntity = repository.save(updatingTarget);
            RP response = mapper.map(updatedEntity, typeOfResponse);

            return ResponseBuilder.okResponse("Update successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when update", e);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<Object>> delete(ID id) {
        try {
            T deletingTarget = repository.findById(id).orElse(null);
            if (deletingTarget == null) {
                return ResponseBuilder.badRequestResponse("Cannot find the record with ID " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            repository.deleteById(id);

            return ResponseBuilder.okResponse("Delete successfully!",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when delete id {}", id, e);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
