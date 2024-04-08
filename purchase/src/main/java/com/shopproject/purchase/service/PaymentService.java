package com.shopproject.purchase.service;

import java.math.BigDecimal;

public interface PaymentService {
    Integer getRandomChance();
    boolean refundMoney(BigDecimal price);
    boolean makePay(BigDecimal price);
}
