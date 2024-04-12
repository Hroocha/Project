package com.shopproject.purchase.service;

import com.shopproject.purchase.exeptions.PaymentGatewayException;

import java.math.BigDecimal;

public interface PaymentService {
    void refundMoney(BigDecimal price) throws PaymentGatewayException;
    void makePay(BigDecimal price) throws PaymentGatewayException;
}
