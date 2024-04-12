package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.exeptions.PaymentGatewayException;
import com.shopproject.purchase.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Boolean isPaymentFail() {
        Random rand = new Random();
        return (rand.nextInt(10000) + 1) == 1;
    }

    @Override
    public void refundMoney(BigDecimal price){
        if (isPaymentFail()) {
            throw new PaymentGatewayException("Платежный шлюз упал");
        }
    }

    @Override
    public void makePay(BigDecimal price){
        if (isPaymentFail()) {
            throw new PaymentGatewayException("Платежный шлюз упал");
        }
    }




}
