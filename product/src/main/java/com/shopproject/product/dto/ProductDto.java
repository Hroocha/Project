package com.shopproject.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductDto {
    private UUID id;
    private BigDecimal price;
    private Integer guaranteePeriod;
}
