package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoController {
    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<InventoryResponse>>> demo(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return inventoryService.getAll(page, size);
    }
}
