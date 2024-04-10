package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.SalesRequest;
import com.shopproject.purchase.dtos.SalesResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public interface StatisticService {

    SalesResponse getSales(LocalDateTime from, LocalDateTime to);

    SalesResponse getSalesByProductId(UUID productId, LocalDateTime from, LocalDateTime to);

    Double getAverageBill (SalesRequest salesRequest);

    Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest);
}
