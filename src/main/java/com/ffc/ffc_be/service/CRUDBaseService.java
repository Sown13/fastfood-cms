package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.ICRUDBaseRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

@AllArgsConstructor
@RequiredArgsConstructor
@CustomLog
@Service
public abstract class CRUDBaseService<T, ID, RQ, RP> {
    private final ICRUDBaseRepository<T, ID> repository;
    private final ModelMapper mapper;
    private Class<T> typeOfModel = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<RQ> typeOfRequest = (Class<RQ>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private Class<RP> typeOfResponse = (Class<RP>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

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

    public ResponseEntity<ResponseDto<RP>> addNew(RQ request) {
        try {
            T newRecord = mapper.map(request, typeOfModel);
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

    public ResponseEntity<ResponseDto<Object>> update(RQ request, ID id) {
        try {
            T updatingTarget = repository.findById(id).orElse(null);
            if (updatingTarget == null) {
                return ResponseBuilder.badRequestResponse("Cannot find the record with ID " + id,
                        StatusCodeEnum.STATUSCODE2001);
            }

            mapper.map(request, updatingTarget);

            T updatedEntity = repository.save(updatingTarget);

            return ResponseBuilder.okResponse("Update successfully!",
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

            deletingTarget.getClass().getMethod("isDeleted", boolean.class).invoke(deletingTarget, false);

            T deletedEntity = repository.save(deletingTarget);

            return ResponseBuilder.okResponse("Delete successfully!",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            log.error("Failed when delete id {}", id, e);

            return ResponseBuilder.badRequestResponse("Request Failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
