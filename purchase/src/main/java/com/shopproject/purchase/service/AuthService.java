package com.shopproject.purchase.service;

/**
 * ������ �������� �� �����������
 */
public interface AuthService {
    /**
     * ����� �������� ����� ��������� ������/����� ������������ ������������
     * @return �����
     */
    String getTokenByTechnicalUser();
}
