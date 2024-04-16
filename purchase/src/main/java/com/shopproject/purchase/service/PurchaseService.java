package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.dtos.PurchaseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Сервис отвечает за логику работы с покупками
 */
public interface PurchaseService {

    /**
     * Получить все заказы конкретного пользователя
     *
     * @param pageNumber Номер страницы
     * @param pageSize Количество заказов на одной странице
     * @return Страницу с описаниями заказов пользователя
     */
    Page<PurchaseDto> getOrdersByUser(int pageNumber, int pageSize);

    /**
     * Купить товар
     *
     * @param productId Идентификатор товара
     * @return Описание товара
     */
    ProductDto buy(UUID productId);

    /**
     * Вернуть товар
     *
     * @param purchaseId Идентификатор покупки
     * @return Описание гарантии по которой возвращается покупка
     */
    GuaranteeDto refund(UUID purchaseId);
}
