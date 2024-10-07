package com.ffc.ffc_be.model.dto.puredto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImExDetailDto {
    @NotNull
    @Min(1)
    private Integer quantity;

    private Integer materialId;
    private LocalDateTime factoryDate;
    private String note;
    private Double totalValue;

    private Integer importTargetId;
}
