package com.shopproject.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse (
        UUID id,
        String name,
        BigDecimal price,
        Integer guaranteePeriod,
        byte[] picture,
        Integer quantity
){
}
