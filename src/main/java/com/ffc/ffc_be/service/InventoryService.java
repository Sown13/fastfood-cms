package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.CreateInventoryHistoryRequest;
import com.ffc.ffc_be.model.dto.response.InventoryHistoryListResponse;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.model.entity.InventoryHistoryDetailModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IInventoryHistoryDetailRepository;
import com.ffc.ffc_be.repository.IInventoryHistoryRepository;
import com.ffc.ffc_be.repository.IInventoryRepository;
import com.ffc.ffc_be.transaction.InventoryHistoryTransaction;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@CustomLog
public class InventoryService {
    private final IInventoryRepository inventoryRepository;
    private final InventoryHistoryTransaction inventoryHistoryTransaction;
    private final IInventoryHistoryRepository inventoryHistoryRepository;
    private final IInventoryHistoryDetailRepository inventoryHistoryDetailRepository;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<List<InventoryResponse>>> getCurrentInventory(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<InventoryResponse> result = inventoryRepository.getNewestInventory(pageable);
            List<InventoryResponse> response = result.getContent();

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

    public ResponseEntity<ResponseDto<Object>> getInventoryHistory() {
        return null;
    }

    public ResponseEntity<ResponseDto<Object>> createInventoryHistory(CreateInventoryHistoryRequest request) {
        try {
            if (inventoryHistoryTransaction.createInventoryHistory(request)) {
                return ResponseBuilder.okResponse("Closing Inventory successfully!",
                        StatusCodeEnum.STATUSCODE1001);
            }

            return ResponseBuilder.badRequestResponse("Closing Inventory failed!",
                    StatusCodeEnum.STATUSCODE2001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error happen when closing Inventory failed",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    // Auto closing inventory at the end of the day
    @Scheduled(cron = "0 17 16 * * ?")
    private void autoCloseInventoryAtTheEndOfTheDayIfNotManualClosingOnThatDay() {
        String description = "Auto close inventory at the end of the day " + LocalDate.now().minusDays(1);
        CreateInventoryHistoryRequest request = new CreateInventoryHistoryRequest(description, false);
        try {
            inventoryHistoryTransaction.createInventoryHistory(request);

            String logMessage = description + " Successfully!";
            log.info(logMessage);
        } catch (Exception e) {
            String logMessage = "Error when" + description;
            log.error(logMessage);
        }
    }

    public ResponseEntity<ResponseDto<List<InventoryHistoryListResponse>>> getInventoryHistoryList(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<InventoryHistoryListResponse> result = inventoryHistoryRepository.getInventoryHistoryList(pageable);
            List<InventoryHistoryListResponse> response = result.getContent();

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get inventory history list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error happen when get inventory history list",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<List<InventoryHistoryDetailModel>>> getInventoryHistoryListDetail(Integer inventoryHistoryId, Integer page, Integer size) {
        if (inventoryHistoryId == null || inventoryHistoryId < 0) {
            return ResponseBuilder.badRequestResponse("Inventory history id invalid!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        try {
            Page<InventoryHistoryDetailModel> result = inventoryHistoryDetailRepository.findInventoryHistoryDetailModelsByInventoryHistoryId(inventoryHistoryId, pageable);
            List<InventoryHistoryDetailModel> inventoryHistoryDetailList = result.getContent();

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get inventory history detail list successfully!",
                    inventoryHistoryDetailList,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData
            );
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error happen when get inventory history detail list",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
