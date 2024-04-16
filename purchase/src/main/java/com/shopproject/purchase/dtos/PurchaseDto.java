package com.shopproject.purchase.dtos;

import com.shopproject.purchase.entities.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseDto(
        UUID id,
        UUID productId,
        BigDecimal price,
        Integer guaranteePeriod,
        LocalDate dateOfPurchase,
        Status status,
        String comment
) {
}
