package com.shopproject.guarantee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class GuaranteeDto {
    private UUID purchaseId;
    private LocalDate validUntil;
}
