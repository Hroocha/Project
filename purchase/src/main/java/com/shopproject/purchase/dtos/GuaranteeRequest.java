package com.shopproject.purchase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GuaranteeRequest {
    private UUID purchaseId;
    private Integer validInMonth;
}
