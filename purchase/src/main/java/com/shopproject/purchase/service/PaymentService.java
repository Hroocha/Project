package com.shopproject.purchase.service;

import com.shopproject.purchase.exeptions.GuaranteeException;
import com.shopproject.purchase.exeptions.PaymentGatewayException;

import java.math.BigDecimal;

/**
 * Сервис отвечает за оплату
 */
public interface PaymentService {

    /**
     * Вернуть покупателю стоимость покупки
     *
     * @param price Содержит сумму, которую необходимо вернуть
     * @throws PaymentGatewayException В случае, если не удается вернуть сумму
     */
    void refundMoney(BigDecimal price) throws PaymentGatewayException;

    /**
     * Оплатить стоимость покупки
     *
     * @param price Содержит сумму, которую необходимо списать
     * @throws PaymentGatewayException В случае, если не удается провести оплату
     */
    void makePay(BigDecimal price) throws PaymentGatewayException;
}
