package com.shopproject.purchase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SalesRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
