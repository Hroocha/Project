package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.SalesRequest;
import com.shopproject.purchase.dtos.SalesResponse;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * ������ �������� �� ������ ��������� ���������� �� ��������
 */
public interface StatisticService {

    /**
     * �������� ������ ������� �� ������ �������
     *
     * @param from ���� ������ �������
     * @param to ���� ��������� �������
     * @return ���������� � �������� �� ��������� ������
     */
    SalesResponse getSales(LocalDateTime from, LocalDateTime to);

    /**
     * �������� ������ ������� �� ����������� ������ �� ������ �������
     *
     * @param productId ������������� ������
     * @param from ���� ������ �������
     * @param to ���� ��������� �������
     * @return ���������� � �������� ����������� ������ �� ��������� ������
     */
    SalesResponse getSalesByProductId(UUID productId, LocalDateTime from, LocalDateTime to);

    /**
     * �������� ������� ��� �� �������� ���� ������� �� ��������� ������
     *
     * @param salesRequest ���������� � ������� �� �������� ����� ����������
     * @return ������� ��������� ������� �� ������
     */
    Double getAverageBill (SalesRequest salesRequest);

    /**
     * �������� ������� ��� �� ������������ ������������ �� ��������� ������
     *
     * @param userId ������������� ������������
     * @param salesRequest ���������� � ������� �� �������� ����� ����������
     * @return ������� ��������� ������� ����������� ������������ �� ������
     */
    Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest);
}
