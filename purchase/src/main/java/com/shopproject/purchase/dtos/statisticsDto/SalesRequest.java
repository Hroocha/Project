package com.shopproject.purchase.dtos.statisticsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SalesRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
