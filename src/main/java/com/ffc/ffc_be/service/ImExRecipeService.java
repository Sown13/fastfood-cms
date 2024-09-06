package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.transaction.ImExTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImExRecipeService {
    private final ImExTransaction imExTransaction;

    public ResponseEntity<ResponseDto<Object>> createImEx(ImExCreateRequest request) {
        try {
            List<ImExDetailModel> resultList = imExTransaction.createNewImExRecipe(request);
            if (resultList == null || resultList.isEmpty()) {
                return ResponseBuilder.badRequestResponse("Failed to create im/ex recipe", StatusCodeEnum.STATUSCODE2001);
            }

            return ResponseBuilder.okResponse("Create im/ex recipe successfully!", StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed to create im/ex recipe",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
