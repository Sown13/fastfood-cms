package com.ffc.ffc_be.controller.crud.cms;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.InventoryHistoryCreateRequest;
import com.ffc.ffc_be.model.dto.response.InventoryHistoryDetailResponse;
import com.ffc.ffc_be.model.dto.response.InventoryHistoryListResponse;
import com.ffc.ffc_be.model.dto.response.InventoryResponse;
import com.ffc.ffc_be.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "Get newest inventory info, start from page 0",
            description = "Get all, get metaData in response, page start from 0, default page =0, size = 10"
    )
    public ResponseEntity<ResponseDto<List<InventoryResponse>>> getInventory(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return inventoryService.getCurrentInventory(page, size);
    }

    @PostMapping
    @Operation(summary = "Closing inventory (create inventory history)",
            description = "Closing inventory, isManualClosing = true"
    )
    public ResponseEntity<ResponseDto<Object>> manualClosingInventory(@RequestBody @Valid InventoryHistoryCreateRequest request) {
        return inventoryService.createInventoryHistory(request);
    }

    @GetMapping("/history-list")
    @Operation(summary = "Get inventory history list, start from page 0",
            description = "Get all, get metaData in response, page start from 0, default page =0, size = 10"
    )
    public ResponseEntity<ResponseDto<List<InventoryHistoryListResponse>>> getInventoryHistoryList(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return inventoryService.getInventoryHistoryList(page, size);
    }

    @GetMapping("/history-list/{inventoryHistoryId}")
    @Operation(summary = "Get inventory history detail list by history id",
            description = "Get all, get metaData in response, page start from 0, default page =0, size = 10"
    )
    public ResponseEntity<ResponseDto<InventoryHistoryDetailResponse>> getInventoryHistoryDetailListByHistoryId(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                                                @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                                                                                @PathVariable Integer inventoryHistoryId) {
        return inventoryService.getInventoryHistoryListDetail(inventoryHistoryId, page, size);
    }
}