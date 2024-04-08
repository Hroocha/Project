package com.shopproject.product.dto;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class ProductResponse {
    private UUID id;

    private String name;

    private BigDecimal price;

    private Integer guaranteePeriod;

    private byte[] picture;

    private String quantity;

}
