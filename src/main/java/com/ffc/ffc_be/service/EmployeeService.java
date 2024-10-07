package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.EmployeeResponse;
import com.ffc.ffc_be.model.dto.response.MaterialResponse;
import com.ffc.ffc_be.model.entity.MaterialModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final IUserInfoRepository userInfoRepository;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<List<EmployeeResponse>>> getAll(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            Pageable pageable = PageRequest.of(page, size);

            Page<UserCmsInfoModel> result = userInfoRepository.findAll(pageable);
            List<EmployeeResponse> response = result.stream()
                    .map(entity -> mapper.map(entity, EmployeeResponse.class))
                    .collect(Collectors.toList());

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get list employee successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed when fetch data",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
