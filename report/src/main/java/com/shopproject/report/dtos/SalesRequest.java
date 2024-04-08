package com.shopproject.report.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRequest {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
