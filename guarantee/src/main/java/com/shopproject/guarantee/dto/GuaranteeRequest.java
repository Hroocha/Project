package com.shopproject.guarantee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GuaranteeRequest {

    private UUID purchaseId;
    private Integer guaranteePeriod;
}
