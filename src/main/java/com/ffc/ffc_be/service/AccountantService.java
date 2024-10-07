package com.ffc.ffc_be.service;

import com.ffc.ffc_be.model.builder.ResponseBuilder;
import com.ffc.ffc_be.model.builder.ResponseDto;
import com.ffc.ffc_be.model.dto.response.AccountantReportResponse;
import com.ffc.ffc_be.model.entity.AccountAssetModel;
import com.ffc.ffc_be.model.entity.AccountEquityModel;
import com.ffc.ffc_be.model.enums.AccountCalculateType;
import com.ffc.ffc_be.model.enums.StatusCodeEnum;
import com.ffc.ffc_be.repository.IAccountAssetRepository;
import com.ffc.ffc_be.repository.IAccountEquityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountantService {
    private final IAccountAssetRepository assetRepository;
    private final IAccountEquityRepository equityRepository;

    public ResponseEntity<ResponseDto<AccountantReportResponse>> getAccountantReport() {
        try {
            List<AccountEquityModel> equityList = equityRepository.findAll();
            List<AccountAssetModel> assetList = assetRepository.findAll();

            double totalAssetValue = 0;
            double totalCapitalValue = 0;
            double totalRevenue = 0;
            double totalProfit = 0;
            double totalCost = 0;

            for (AccountEquityModel equity : equityList) {
                if (equity.getCalculateType().equals(AccountCalculateType.INCREASE)) {
                    if (equity.getAccountNumber().equals("641")) {
                        totalCost += equity.getAmount();
                    } else if (equity.getAccountNumber().equals("511")) {
                        totalRevenue += equity.getAmount();
                    } else if (equity.getAccountNumber().equals("411")) {
                        totalCapitalValue += equity.getAmount();
                    }
                } else {
                    if (equity.getAccountNumber().equals("641")) {
                        totalCost -= equity.getAmount();
                    } else if (equity.getAccountNumber().equals("511")) {
                        totalRevenue -= equity.getAmount();
                    } else if (equity.getAccountNumber().equals("411")) {
                        totalCapitalValue -= equity.getAmount();
                    }
                }

            }

            for (AccountAssetModel asset : assetList) {
                if (asset.getAccountNumber().equals("111")) {
                    if (asset.getCalculateType().equals(AccountCalculateType.INCREASE)) {
                        totalAssetValue += asset.getAmount();
                    } else {
                        totalAssetValue -= asset.getAmount();
                    }
                }
            }

            totalProfit = totalRevenue - totalCost;

            AccountantReportResponse response = AccountantReportResponse.builder()
                    .totalAssetValue(totalAssetValue)
                    .totalCapitalValue(totalCapitalValue)
                    .totalProfit(totalProfit)
                    .totalRevenue(totalRevenue)
                    .totalCost(totalCost)
                    .build();

            return ResponseBuilder.okResponse("Get business report successfully!",
                    response,
                    StatusCodeEnum.STATUSCODE1001);

        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse("Failed when get report",
                    StatusCodeEnum.STATUSCODE2001);
        }
    }
}
