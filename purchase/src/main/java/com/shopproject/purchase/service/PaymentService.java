package com.shopproject.purchase.service;

import com.shopproject.purchase.exeptions.GuaranteeException;
import com.shopproject.purchase.exeptions.PaymentGatewayException;

import java.math.BigDecimal;

/**
 * ������ �������� �� ������
 */
public interface PaymentService {

    /**
     * ������� ���������� ��������� �������
     *
     * @param price �������� �����, ������� ���������� �������
     * @throws PaymentGatewayException � ������, ���� �� ������� ������� �����
     */
    void refundMoney(BigDecimal price) throws PaymentGatewayException;

    /**
     * �������� ��������� �������
     *
     * @param price �������� �����, ������� ���������� �������
     * @throws PaymentGatewayException � ������, ���� �� ������� �������� ������
     */
    void makePay(BigDecimal price) throws PaymentGatewayException;
}
