package com.shopproject.report.service;

import com.shopproject.report.dtos.SalesRequest;
import com.shopproject.report.dtos.SalesResponse;

import java.util.UUID;

public interface ReportService {

    SalesResponse getSales(SalesRequest salesRequest);

    SalesResponse getSalesByProductId(UUID productId, SalesRequest salesRequest);

    Double getAverageBill(SalesRequest salesRequest);

    Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest);
}
