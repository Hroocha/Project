package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Integer getRandomChance() {
        Random rand = new Random();
        return rand.nextInt(10000) + 1;
    }

    @Override
    public boolean refundMoney(BigDecimal price){
        return getRandomChance() != 1;
    }

    @Override
    public boolean makePay(BigDecimal price){
        return getRandomChance() != 1;
    }




}
