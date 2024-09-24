package com.ffc.ffc_be.transaction;

import com.ffc.ffc_be.model.dto.request.CreateInventoryHistoryRequest;
import com.ffc.ffc_be.model.entity.InventoryHistoryDetailModel;
import com.ffc.ffc_be.model.entity.InventoryHistoryModel;
import com.ffc.ffc_be.model.entity.InventoryModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.repository.IInventoryHistoryDetailRepository;
import com.ffc.ffc_be.repository.IInventoryHistoryRepository;
import com.ffc.ffc_be.repository.IInventoryRepository;
import com.ffc.ffc_be.service.UserCmsInfoService;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@CustomLog
public class InventoryHistoryTransaction {
    private final IInventoryHistoryRepository inventoryHistoryRepository;
    private final IInventoryHistoryDetailRepository inventoryHistoryDetailRepository;
    private final IInventoryRepository inventoryRepository;
    private final UserCmsInfoService userCmsInfoService;
    private final ModelMapper mapper;

    @Transactional
    public boolean createInventoryHistory(CreateInventoryHistoryRequest request) {
        UserCmsInfoModel userCmsInfoModel = null;
        if (request.getIsManualClosing()) {
            try {
                userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
                if (userCmsInfoModel == null) {
                    log.info("Cannot get user info from context");

                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }

        List<InventoryModel> currentInventory;
        try {
            currentInventory = inventoryRepository.findAll();
        } catch (Exception e) {
            log.error("Failed when get current current inventory", e);

            return false;
        }

        String historyName;
        if (request.getIsManualClosing()) {
            historyName = "[MANUAL] Closing Inventory - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            historyName = "[AUTO] Closing Inventory - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        InventoryHistoryModel inventoryHistoryModel;
        if (request.getIsManualClosing() && userCmsInfoModel != null) {
            inventoryHistoryModel = InventoryHistoryModel.builder()
                    .reportedBy(userCmsInfoModel.getId())
                    .name(historyName)
                    .description(request.getDescription())
                    .reportedDate(LocalDateTime.now())
                    .build();
        } else {
            inventoryHistoryModel = InventoryHistoryModel.builder()
                    .name(historyName)
                    .description(request.getDescription())
                    .reportedDate(LocalDateTime.now())
                    .build();
        }
        InventoryHistoryModel savedInventoryHistoryModel = inventoryHistoryRepository.save(inventoryHistoryModel);

        List<InventoryHistoryDetailModel> inventoryHistoryDetailModelList = new ArrayList<>();
        for (InventoryModel inventoryModel : currentInventory) {
            InventoryHistoryDetailModel detailHistory = mapper.map(inventoryModel, InventoryHistoryDetailModel.class);
            detailHistory.setId(null);//since mapper also map id from inventoryModel so need to clear it or it will be update instead of insert
            detailHistory.setInventoryHistoryId(savedInventoryHistoryModel.getId());
            detailHistory.setNote("Auto created when closing inventory");
            inventoryHistoryDetailModelList.add(detailHistory);
        }
        inventoryHistoryDetailRepository.saveAll(inventoryHistoryDetailModelList);

        return true;
    }
}
