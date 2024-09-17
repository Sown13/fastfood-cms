package com.ffc.ffc_be.model.dto.request;

import com.ffc.ffc_be.annotation.IsValidClosingInventoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInventoryHistoryRequest {
    private String description;

    @IsValidClosingInventoryType
    private Boolean isManualClosing;
}
