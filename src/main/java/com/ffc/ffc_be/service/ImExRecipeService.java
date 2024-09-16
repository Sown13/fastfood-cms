package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.puredto.ImExDetailHistoryDto;
import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IImExDetailRepository;
import com.ffc.ffc_be.transaction.ImExTransaction;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@CustomLog
public class ImExRecipeService {
    private final ImExTransaction imExTransaction;
    private final IImExDetailRepository imExDetailRepository;

    public ResponseEntity<ResponseDto<Object>> createImEx(ImExCreateRequest request) {
        try {
            List<ImExDetailModel> resultList = imExTransaction.createNewImExRecipe(request);
            if (resultList == null || resultList.isEmpty()) {
                return ResponseBuilder.badRequestResponse("Failed to create im/ex recipe", StatusCodeEnum.STATUSCODE2001);
            }

            return ResponseBuilder.okResponse("Create im/ex recipe successfully!",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed to create im/ex recipe",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<ImExDetailHistoryResponse>> getImExDetailHistoryList(Integer page, Integer size, RepTypeEnum repType) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            List<ImExDetailHistoryDto> results = imExDetailRepository.getImExDetailHistoryList(repType, pageable);
            long totalElements = imExDetailRepository.countImExDetailHistory(repType);
            int totalPages = totalElements % size == 0 ? (int) totalElements / size : (int) (totalElements / size + 1);
            ImExDetailHistoryResponse response = new ImExDetailHistoryResponse();
            response.setImExDetailHistoryList(results);

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) totalElements)
                    .totalPage(totalPages)
                    .build();

            return ResponseBuilder.okResponse("Get im-ex detail list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);
        } catch (Exception e) {
            log.error("Error when get im-ex list", e);

            return ResponseBuilder.badRequestResponse("Failed to get im-ex list",
                    StatusCodeEnum.STATUSCODE2001);
        }


    }
}
