package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailDto;
import com.ffc.ffc_be.model.dto.puredto.MenuDishDetailMaterialDto;
import com.ffc.ffc_be.model.dto.request.CreateMenuDishRequest;
import com.ffc.ffc_be.model.dto.response.MenuDishRecipeResponse;
import com.ffc.ffc_be.model.entity.MenuDishDetailModel;
import com.ffc.ffc_be.model.entity.MenuDishModel;
import com.ffc.ffc_be.model.entity.UserCmsInfoModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IMenuDishDetailRepository;
import com.ffc.ffc_be.repository.IMenuDishRepository;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@CustomLog
@RequiredArgsConstructor
public class MenuDishService {
    private final IMenuDishRepository menuDishRepository;
    private final IMenuDishDetailRepository menuDishDetailRepository;
    private final UserCmsInfoService userCmsInfoService;
    private final ModelMapper mapper;

    public ResponseEntity<ResponseDto<List<MenuDishModel>>> getAllMenuDishesByCondition(Integer page, Integer size, Boolean isActivate) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("category").descending());

            Page<MenuDishModel> result;
            if (isActivate == null) {
                result = menuDishRepository.findAll(pageable);
            } else if (isActivate) {
                result = menuDishRepository.findAllByIsActivateTrue(pageable);
            } else {
                result = menuDishRepository.findAllByIsActivateFalse(pageable);
            }

            List<MenuDishModel> response = result.getContent();

            MetaData metaData = MetaData.builder()
                    .currentPage(page)
                    .pageSize(size)
                    .totalItems((int) result.getTotalElements())
                    .totalPage(result.getTotalPages())
                    .build();

            return ResponseBuilder.okResponse("Get menu dish list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001,
                    metaData);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Get menu dish list failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    public ResponseEntity<ResponseDto<MenuDishRecipeResponse>> getMenuRecipe(Integer menuId) {
        if (menuId == null || menuId < 0) {
            return ResponseBuilder.badRequestResponse("Invalid menuId!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        try {
            List<MenuDishDetailMaterialDto> result = menuDishDetailRepository.findRecipeMaterialByMenuId(menuId);

            MenuDishModel menuDishModel = menuDishRepository.findById(menuId).orElse(null);
            if (menuDishModel == null) {
                return ResponseBuilder.badRequestResponse("Menu not found!",
                        StatusCodeEnum.STATUSCODE2001);
            }

            MenuDishRecipeResponse response = mapper.map(menuDishModel, MenuDishRecipeResponse.class);
            response.setMaterialList(result);

            return ResponseBuilder.okResponse("Get menu dish list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Get menu dish detail material failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }

    @Transactional
    public ResponseEntity<ResponseDto<Object>> createMenuDish(CreateMenuDishRequest request) {
        UserCmsInfoModel userCmsInfoModel;
        try {
            userCmsInfoModel = userCmsInfoService.getUserInfoFromContext();
            if (userCmsInfoModel == null) {
                log.info("Cannot get user info from context");

                return ResponseBuilder.badRequestResponse("Wrong user cms info!",
                        StatusCodeEnum.STATUSCODE2001);
            }
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error when get user info from context!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        MenuDishModel menuDishCreated;
        try {
            Integer cookTime = request.getMenuDishDetailList().stream()
                    .mapToInt(MenuDishDetailDto::getPrepareTime)
                    .sum();

            MenuDishModel newMenuDishModel = MenuDishModel.builder()
                    .name(request.getName())
                    .descriptionPublic(request.getDescriptionPublic())
                    .descriptionPrivate(request.getDescriptionPrivate())
                    .cookTime(cookTime)
                    .price(request.getPrice())
                    .category(request.getCategory())
                    .createdBy(userCmsInfoModel.getId())
                    .isActivate(true)
                    .build();
            menuDishCreated = menuDishRepository.save(newMenuDishModel);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error when get create menu dish!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        try {
            List<MenuDishDetailModel> menuDishDetailModelList = new ArrayList<>();
            for (MenuDishDetailDto dto : request.getMenuDishDetailList()) {
                MenuDishDetailModel model = MenuDishDetailModel.builder()
                        .materialId(dto.getMaterialId())
                        .menuDishId(menuDishCreated.getId())
                        .quantity(dto.getQuantity())
                        .note(dto.getNote())
                        .prepareTime(dto.getPrepareTime())
                        .build();
                menuDishDetailModelList.add(model);
            }

            menuDishDetailRepository.saveAll(menuDishDetailModelList);

            return ResponseBuilder.okResponse("Create menu successfully!",
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Error when get create menu dish detail!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
