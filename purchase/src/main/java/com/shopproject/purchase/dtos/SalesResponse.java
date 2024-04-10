package com.shopproject.purchase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SalesResponse {
    private String name;
    private int quantity;
    private Double total;
}
