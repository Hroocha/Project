package com.shopproject.report.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesResponse {
    private int quantity;
    private Double total;
}
