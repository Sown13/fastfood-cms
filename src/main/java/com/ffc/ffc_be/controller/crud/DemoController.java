package com.ffc.ffc_be.controller.crud;

import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.request.ImExCreateRequest;
import com.ffc.ffc_be.model.dto.response.ImExDetailHistoryResponse;
import com.ffc.ffc_be.model.dto.response.ImExRecipeResponse;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import com.ffc.ffc_be.repository.IImExDetailRepository;
import com.ffc.ffc_be.repository.IImExRecipeRepository;
import com.ffc.ffc_be.service.ImExRecipeService;
import com.ffc.ffc_be.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/demo")
public class DemoController {
    private final InventoryService inventoryService;
    private final ImExRecipeService imExRecipeService;
    private final IImExDetailRepository imExDetailRepository;
    private final IImExRecipeRepository imExRecipeRepository;
    private final ModelMapper mapper;

    @GetMapping
    public Page<ImExRecipeResponse> demo(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                         @RequestParam(name = "repType", required = true, defaultValue = "IMPORT") RepTypeEnum repType) {
        Pageable pageable = PageRequest.of(page, size);
        return imExRecipeRepository.getImExRecipeList(repType, pageable);
    }

    @PostMapping("/im-ex")
    public ResponseEntity<ResponseDto<Object>> demoImEx(@RequestBody @Valid ImExCreateRequest request) {
        return imExRecipeService.createImEx(request);
    }

    @GetMapping("/im-ex-list")
    public ResponseEntity<ResponseDto<List<ImExDetailHistoryResponse>>> demoImExDetail(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                                       @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                                                       @RequestParam(name = "repType", required = true, defaultValue = "IMPORT") RepTypeEnum repType) {
        return imExRecipeService.getImExDetailHistoryList(page, size, repType);
    }
}
