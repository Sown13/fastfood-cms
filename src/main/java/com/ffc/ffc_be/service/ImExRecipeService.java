package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.puredto.ImExRecipeDetailDto;
import com.ffc.ffc_be.model.dto.request.ImExRecipeCreateRequest;
import com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse;
import com.ffc.ffc_be.model.dto.response.ImExRecipeDetailResponse;
import com.ffc.ffc_be.model.dto.response.ImExRecipeResponse;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import com.ffc.ffc_be.model.entity.ImExRecipeModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IImExDetailRepository;
import com.ffc.ffc_be.repository.IImExRecipeRepository;
import com.ffc.ffc_be.repository.IUserInfoRepository;
import com.ffc.ffc_be.transaction.ImExTransaction;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    private final IImExRecipeRepository imExRecipeRepository;
    private final IUserInfoRepository userInfoRepository;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<Object>> createImEx(ImExRecipeCreateRequest request) {
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

    public ResponseEntity<ResponseDto<List<ImExDetailHistoryResponse>>> getImExDetailHistoryList(Integer page, Integer size, RepTypeEnum repType) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            List<ImExDetailHistoryResponse> response = imExDetailRepository.getImExDetailHistoryList(repType, pageable);
            long totalElements = imExDetailRepository.countImExDetailHistory(repType);
            int totalPages = totalElements % size == 0 ? (int) totalElements / size : (int) (totalElements / size + 1);

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

    public ResponseEntity<ResponseDto<List<ImExRecipeResponse>>> getImExRecipeList(Integer page, Integer size, RepTypeEnum repType) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

            Page<ImExRecipeResponse> result = imExRecipeRepository.getImExRecipeList(repType, pageable);
            List<ImExRecipeResponse> response = result.getContent();

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get im-ex recipe successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Get im-ex recipe failed",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<ImExRecipeDetailResponse>> getImExRecipeDetail(Integer recipeId) {
        ImExRecipeModel imExRecipeModel;
        try {
            imExRecipeModel = imExRecipeRepository.findById(recipeId).orElse(null);
            if (imExRecipeModel == null) {
                return ResponseBuilder.badRequestResponse("Recipe not found!",
                        StatusCodeEnum.STATUSCODE2001);
            }
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error when get im-ex recipe record",
                    StatusCodeEnum.STATUSCODE2001);
        }

        UserCmsInfoModel userCreate = null;
        UserCmsInfoModel userResponsible = null;
        try {
            userCreate = userInfoRepository.findById(imExRecipeModel.getCreatedBy()).orElse(null);
            userResponsible = userInfoRepository.findById(imExRecipeModel.getResponsibleBy()).orElse(null);
        } catch (Exception e) {
            log.error("Error when get created by and responsible by user", e);
        }

        List<ImExRecipeDetailDto> recipeDetail;
        try {
            recipeDetail = imExDetailRepository.getImExRecipeDetailByRecipeId(recipeId);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error when get recipe detail!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        ImExRecipeDetailResponse response;
        try {
            response = mapper.map(imExRecipeModel, ImExRecipeDetailResponse.class);
            response.setImExDetailList(recipeDetail);

            if (userCreate != null) {
                response.setCreatedByName(userCreate.getFullName());
            }

            if (userResponsible != null) {
                response.setResponsibleName(userResponsible.getFullName());
            }
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed when map data to response!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        return ResponseBuilder.okResponse("Get im-ex recipe detail successfully!",
                response,
                StatusCodeEnum.STATUSCODE1001);
    }
}
