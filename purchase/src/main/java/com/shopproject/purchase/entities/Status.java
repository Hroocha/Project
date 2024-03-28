package com.shopproject.purchase.entities;


public enum Status {
    CREATE,
    PAID,
    GUARANTEE_ERROR,
    PAYMENT_ERROR,
    PRODUCT_ERROR,
    PURCHASED,
    REFUND,
    PURCHASE_ERROR;
    }
 // создан, оплачен, ошибка гарантии, ошибка оплаты, ошибка товара, куплен, возвращен, ошибка покупки