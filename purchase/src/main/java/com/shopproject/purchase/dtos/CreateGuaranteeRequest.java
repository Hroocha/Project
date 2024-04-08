package com.shopproject.purchase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateGuaranteeRequest {
    private UUID purchaseId;
    private Integer guaranteePeriod;
}
