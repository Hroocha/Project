package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.dtos.PurchaseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * ������ �������� �� ������ ������ � ���������
 */
public interface PurchaseService {

    /**
     * �������� ��� ������ ����������� ������������
     *
     * @param pageNumber ����� ��������
     * @param pageSize ���������� ������� �� ����� ��������
     * @return �������� � ���������� ������� ������������
     */
    Page<PurchaseDto> getOrdersByUser(int pageNumber, int pageSize);

    /**
     * ������ �����
     *
     * @param productId ������������� ������
     * @return �������� ������
     */
    ProductDto buy(UUID productId);

    /**
     * ������� �����
     *
     * @param purchaseId ������������� �������
     * @return �������� �������� �� ������� ������������ �������
     */
    GuaranteeDto refund(UUID purchaseId);
}
