package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.MetaData;
import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.entity.MenuDishDetailModel;
import com.ffc.ffc_be.model.entity.MenuDishModel;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IMenuDishDetailRepository;
import com.ffc.ffc_be.repository.IMenuDishRepository;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CustomLog
@RequiredArgsConstructor
public class MenuDishService {
    private final IMenuDishRepository menuDishRepository;
    private final IMenuDishDetailRepository menuDishDetailRepository;

    public ResponseEntity<ResponseDto<List<MenuDishModel>>> getAllMenuDishes(Integer page, Integer size) {
        try {
            if (page == null || page < 0) {
                page = 0;
            }
            if (size == null || size < 1) {
                size = 10;
            }

            Pageable pageable = PageRequest.of(page, size, Sort.by("category").descending());
            Page<MenuDishModel> result = menuDishRepository.findAll(pageable);
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

    public ResponseEntity<ResponseDto<List<MenuDishDetailModel>>> getMenuDetailMaterialsByMenuId(Integer menuId) {
        if (menuId == null || menuId < 0) {
            return ResponseBuilder.badRequestResponse("Invalid menuId!",
                    StatusCodeEnum.STATUSCODE2001);
        }

        try {
            List<MenuDishDetailModel> response = menuDishDetailRepository.findAllByMenuDishId(menuId);

            return ResponseBuilder.okResponse("Get menu dish list successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Get menu dish detail material failed!",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
