package com.ffc.ffc_be.transaction;

import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.model.dto.request.ImExDetailDto;
import com.ffc.ffc_be.model.entity.ImExDetailModel;
import com.ffc.ffc_be.model.entity.ImExRecipeModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.repository.IImExDetailRepository;
import com.ffc.ffc_be.repository.IImExRecipeRepository;
import com.ffc.ffc_be.service.UserCmsInfoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ImExTransaction {
    private static final Logger log = LoggerFactory.getLogger(ImExTransaction.class);
    private final IImExDetailRepository imExDetailRepository;
    private final IImExRecipeRepository imExRecipeRepository;
    private final UserCmsInfoService userCmsInfoService;

    @Transactional(rollbackFor = Exception.class)
    public List<ImExDetailModel> createNewImExRecipe(ImExCreateRequest request) {
        UserCmsInfoModel userCmsInfoModel;
        try {
            userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                log.info("Cannot get user info from context");

                return null;
            }
        } catch (Exception e) {
            return null;
        }

        ImExRecipeModel newImExRecipe = ImExRecipeModel.builder()
                .createdBy(userCmsInfoModel.getId())
                .responsibleBy(userCmsInfoModel.getId()) // temporary set user who create also responsible for this for now
                .description(request.getDescription())
                .repType(request.getRepType())
                .purpose(request.getPurpose())
                .build();

        ImExRecipeModel resultImExRecipe;
        try {
            resultImExRecipe = imExRecipeRepository.save(newImExRecipe);
        } catch (Exception e) {
            log.error("Error when create new ImExRecipe", e);

            throw e;
        }

        try {
            List<ImExDetailModel> imExDetailList = new ArrayList<>();
            for (ImExDetailDto dto : request.getDetailList()) {
                double valuePerUnit = dto.getTotalValue() / dto.getQuantity();
                ImExDetailModel model = ImExDetailModel.builder()
                        .recipeId(resultImExRecipe.getId())
                        .materialId(dto.getMaterialId())
                        .quantity(dto.getQuantity())
                        .factoryDate(dto.getFactoryDate())
                        .note(dto.getNote())
                        .totalValue(dto.getTotalValue())
                        .valuePerUnit(valuePerUnit)
                        .supplier(request.getSupplier())
                        .build();
                imExDetailList.add(model);
            }

            return imExDetailRepository.saveAll(imExDetailList);
        } catch (Exception e) {
            log.error("Error when create new im ex detail", e);

            throw e;
        }
    }
}
