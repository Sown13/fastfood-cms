package com.ffc.ffc_be.model.dto.response;

import com.ffc.ffc_be.model.dto.puredto.ImExDetailHistoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImExDetailHistoryResponse {
    private List<ImExDetailHistoryDto> imExDetailHistoryList;
}
