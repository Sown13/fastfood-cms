package com.ffc.ffc_be.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountantReportResponse {
    private double totalAssetValue;
    private double totalCapitalValue;
    private double totalRevenue;
    private double totalProfit;
    private double totalCost;
}
