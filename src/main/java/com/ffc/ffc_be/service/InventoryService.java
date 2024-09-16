package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IInventoryRepository;
import lombok.CustomLog;
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
@CustomLog
public class InventoryService {
    private final IInventoryRepository inventoryRepository;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<List<InventoryResponse>>> getAll(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<InventoryResponse> result = inventoryRepository.getNewestInventory(pageable);
            List<InventoryResponse> response = result.stream()
                    .map(entity -> mapper.map(entity, InventoryResponse.class))
                    .collect(Collectors.toList());

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get newest inventory successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Request Failed! Error when fetch data",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
