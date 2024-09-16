package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.model.dto.puredto.ImExDetailDto;
import com.ffc.ffc_be.model.enums.PurposeEnum;
import com.ffc.ffc_be.model.enums.RepTypeEnum;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExCreateRequest {
    private Integer responsibleBy;
    private String description;
    private RepTypeEnum repType;
    private PurposeEnum purpose;
    private String supplier;

    @Valid
    private List<ImExDetailDto> detailList;
}
