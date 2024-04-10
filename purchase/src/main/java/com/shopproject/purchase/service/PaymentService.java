package com.shopproject.purchase.service;

import java.math.BigDecimal;

public interface PaymentService {
    boolean refundMoney(BigDecimal price);
    boolean makePay(BigDecimal price);
}
