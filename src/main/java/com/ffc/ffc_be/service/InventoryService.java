package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.InventoryHistoryCreateRequest;
import com.ffc.ffc_be.model.dto.response.InventoryHistoryDetailResponse;
import com.ffc.ffc_be.model.dto.response.InventoryHistoryListResponse;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.model.entity.*;
import com.ffc.ffc_be.model.enums.AccountCalculateType;
import com.ffc.ffc_be.model.enums.QueueStatus;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.*;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
@CustomLog
public class InventoryService {
    private final IInventoryRepository inventoryRepository;
    private final InventoryHistoryTransaction inventoryHistoryTransaction;
    private final IInventoryHistoryRepository inventoryHistoryRepository;
    private final IInventoryHistoryDetailRepository inventoryHistoryDetailRepository;
    private final ModelMapper mapper;
    private final IImExDetailRepository imExDetailRepository;
    private final IAccountEquityRepository accountEquityRepository;

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

            for (InventoryResponse inventoryResponse : response) {
                ImExDetailModel model = imExDetailRepository.findImExDetailModelByQueueStatusAndMaterialId(QueueStatus.HEAD, inventoryResponse.getMaterialId()).orElse(null);
                int daysBetweenInteger = -1;
                double timeLeftPercentage = 100;
                int lifeLeft = -1;
                if (model != null) {
                    long daysBetweenLong = ChronoUnit.DAYS.between(model.getFactoryDate(), LocalDateTime.now());
                    daysBetweenInteger = (int) daysBetweenLong;

                    lifeLeft = inventoryResponse.getShelfLife() - daysBetweenInteger;
                    timeLeftPercentage = (double) lifeLeft / (double) inventoryResponse.getShelfLife() * 100;
                }

                if (timeLeftPercentage < 33.3) {
                    inventoryResponse.setIsExpiredSoon(true);
                } else {
                    inventoryResponse.setIsExpiredSoon(false);
                }

                inventoryResponse.setLifeLeft(lifeLeft);
            }

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

    public ResponseEntity<ResponseDto<Object>> createInventoryHistory(InventoryHistoryCreateRequest request) {
        try {
            if (inventoryHistoryTransaction.createInventoryHistory(request)) {
                calculateQueue();

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
        InventoryHistoryCreateRequest request = new InventoryHistoryCreateRequest(description, false);
        try {
            inventoryHistoryTransaction.createInventoryHistory(request);
            calculateQueue();

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

    public ResponseEntity<ResponseDto<InventoryHistoryDetailResponse>> getInventoryHistoryListDetail(Integer inventoryHistoryId, Integer page, Integer size) {
        if (inventoryHistoryId == null || inventoryHistoryId < 0) {
            return ResponseBuilder.badRequestResponse("Inventory history id invalid!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 100;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        try {
            Page<InventoryHistoryDetailModel> result = inventoryHistoryDetailRepository.findInventoryHistoryDetailModelsByInventoryHistoryId(inventoryHistoryId, pageable);
            List<InventoryHistoryDetailModel> inventoryHistoryDetailList = result.getContent();
            InventoryHistoryModel inventoryHistoryModel = inventoryHistoryRepository.findById(inventoryHistoryId).orElse(null);
            if (inventoryHistoryModel == null) {
                return ResponseBuilder.badRequestResponse("Inventory history not found! id: " + inventoryHistoryId,
                        StatusCodeEnum.STATUSCODE2001);
            }

            InventoryHistoryDetailResponse response = InventoryHistoryDetailResponse.builder()
                    .name(inventoryHistoryModel.getName())
                    .description(inventoryHistoryModel.getDescription())
                    .reportedDate(inventoryHistoryModel.getReportedDate())
                    .reportedBy(inventoryHistoryModel.getReportedBy())
                    .inventoryHistoryDetailList(inventoryHistoryDetailList)
                    .build();

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get inventory history detail list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData
            );
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error happen when get inventory history detail list",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public void calculateQueue() {
        try {
            List<InventoryModel> inventoryList = inventoryRepository.findAll();

            for (InventoryModel dto : inventoryList) {
                calculateQueueForOneMaterial(dto.getMaterialId());
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void calculateQueueForOneMaterial(Integer materialId) {
        try {
            List<ImExDetailModel> importDetailList = imExDetailRepository.findImportListForQueue(materialId);
            ImExDetailModel head = importDetailList.remove(importDetailList.size() - 1);
            importDetailList.addFirst(head);
            Queue<ImExDetailModel> importQueue = new LinkedList<>(importDetailList);

            List<ImExDetailModel> exportDetailList = imExDetailRepository.findExportListForQueue(materialId);
            Queue<ImExDetailModel> exportQueue = new LinkedList<>(exportDetailList);

            List<ImExDetailModel> solvedImport = new ArrayList<>();
            List<ImExDetailModel> solvedExport = new ArrayList<>();

            // ghi nhan chi phi tk 641
            AccountEquityModel accountEquityModel = AccountEquityModel.builder()
                    .accountNumber("641")
                    .calculateType(AccountCalculateType.INCREASE)
                    .description("Recognizing costs when goods are issued from inventory")
                    .amount(0d)
                    .build();

            double accumulatedCost = 0d;

            ImExDetailModel currentExportDetail;
            ImExDetailModel headImportDetail = null;
            ImExDetailModel outQueueImport;
            ImExDetailModel outQueueExport;
            while (!exportQueue.isEmpty()) {
                // as long as there still export then 100% still have import left (since not allow minus inventory)
                currentExportDetail = exportQueue.poll();

                // checking if there is already an import head in queue or not
                if (headImportDetail == null) {
                    headImportDetail = importQueue.poll();
                }

                int difference = currentExportDetail.getQuantity() - headImportDetail.getQuantityLeftInQueue();

                while (difference > 0) {

                    //accountant
                    double valuePerUnit = headImportDetail.getValuePerUnit();
                    int totalUnit = headImportDetail.getQuantityLeftInQueue();
                    accumulatedCost += valuePerUnit * totalUnit;
                    ///

                    outQueueImport = headImportDetail;
                    outQueueImport.setQuantityLeftInQueue(0);
                    outQueueImport.setQueueStatus(QueueStatus.DONE);

                    solvedImport.add(outQueueImport);

                    headImportDetail = importQueue.poll();
                    headImportDetail.setQueueStatus(QueueStatus.HEAD);

                    difference = difference - headImportDetail.getQuantityLeftInQueue();//todo need double check
                }

                if (difference < 0) {

                    //accountant
                    double valuePerUnit = headImportDetail.getValuePerUnit();
                    int totalUnit = headImportDetail.getQuantityLeftInQueue() + difference;
                    accumulatedCost += valuePerUnit * totalUnit;
                    ///

                    int quantityLeftInQueue = -1 * difference;
                    headImportDetail.setQuantityLeftInQueue(quantityLeftInQueue);
                } else {

                    //accountant
                    double valuePerUnit = headImportDetail.getValuePerUnit();
                    int totalUnit = headImportDetail.getQuantityLeftInQueue();
                    accumulatedCost += valuePerUnit * totalUnit;
                    ///

                    outQueueImport = headImportDetail;
                    outQueueImport.setQuantityLeftInQueue(0);
                    outQueueImport.setQueueStatus(QueueStatus.DONE);
                    solvedImport.add(outQueueImport);

                    headImportDetail = null;
                }

                outQueueExport = currentExportDetail;
                outQueueExport.setQueueStatus(QueueStatus.DONE);
                solvedExport.add(outQueueExport);
            }

            //Accountant
            accountEquityModel.setAmount(accumulatedCost);
            //

            if (headImportDetail != null) {
                solvedImport.add(headImportDetail);
            }

            accountEquityRepository.save(accountEquityModel);
            imExDetailRepository.saveAll(solvedImport);
            imExDetailRepository.saveAll(solvedExport);

        } catch (Exception e) {
            throw e;
        }
    }
}
