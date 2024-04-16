package com.shopproject.purchase.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalesResponse {
    private int quantity;
    private Double total;
}
