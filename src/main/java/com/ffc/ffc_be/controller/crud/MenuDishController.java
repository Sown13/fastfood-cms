package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.entity.MenuDishDetailModel;
import com.ffc.ffc_be.model.entity.MenuDishModel;
import com.ffc.ffc_be.service.MenuDishService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dish-menu")
public class MenuDishController {
    private final MenuDishService menuDishService;

    @Operation(summary = "Get menu",
            description = "Get menu list by page"
    )
    @GetMapping
    public ResponseEntity<ResponseDto<List<MenuDishModel>>> getMenuList(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return menuDishService.getAllMenuDishes(page, size);
    }

    @Operation(summary = "Get menu detail",
            description = "Get material list for a dish recipe"
    )
    @GetMapping("/{menuId}")
    public ResponseEntity<ResponseDto<List<MenuDishDetailModel>>> getMenuDetailByMenuId(@PathVariable Integer menuId) {
        return menuDishService.getMenuDetailMaterialsByMenuId(menuId);
    }
}
