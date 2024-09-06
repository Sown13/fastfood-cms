package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.service.ImExRecipeService;
import com.ffc.ffc_be.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoController {
    private final InventoryService inventoryService;
    private final ImExRecipeService imExRecipeService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<InventoryResponse>>> demo(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
        return inventoryService.getAll(page, size);
    }

    @PostMapping("/im-ex")
    public ResponseEntity<ResponseDto<Object>> demoImEx(@RequestBody @Valid ImExCreateRequest request){
        return imExRecipeService.createImEx(request);
    }
}
