package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.CreateGuaranteeRequest;
import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.exeptions.GuaranteeException;

import java.util.UUID;

/**
 * Сервис отвечает за логику работы с гарантиями на товары
 */

public interface GuaranteeService {

    /**
     * Сделать гарантию для купленного товара
     *
     * @param guarantee Содержит информацию, нужную для создания гарантии
     * @throws GuaranteeException В случае, если создать гарантию не получилось
     */
    void createGuarantee(CreateGuaranteeRequest guarantee) throws GuaranteeException;

    /**
     * Сделать гарантию для купленного товара
     *
     * @param purchaseId Идентификатор покупки
     * @throws GuaranteeException В случае, если остановить гарантию не получилось
     */
    void stopGuarantee(UUID purchaseId) throws GuaranteeException;


    /**
     * Получить описание гарантии по ID покупки
     *
     * @param purchaseId Идентификатор покупки
     * @throws GuaranteeException В случае если гарантия не найдена
     * @return Описание гарантии
     */
    GuaranteeDto getGuaranteeByPurchase(UUID purchaseId) throws GuaranteeException;



}
