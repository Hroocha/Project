package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.exeptions.NoMoreProductsInStorageException;
import com.shopproject.purchase.exeptions.ProductException;

import java.util.UUID;

/**
 * Сервис отвечает за логику работы с товарами
 */
public interface ProductService {

//    /**
//     * Получить описание товара по ID
//     * @param id Идентификатор товара
//     * @return Описание товара
//     */
//    ProductDto findById(UUID id);

    /**
     * Достать одну единицу товара со склада
     * @param id Идентификатор товара
     * @throws NoMoreProductsInStorageException В случае, если товар закончился на складе
     * @return Описание товара
     */
    ProductDto takeOne(UUID id) throws NoMoreProductsInStorageException;

    /**
     * Вернуть товар на склад
     * @param id Идентификатор товара
     * @throws ProductException В случае если не удалось вернуть товар на склад
     */
    void putOne(UUID id) throws ProductException;
}
