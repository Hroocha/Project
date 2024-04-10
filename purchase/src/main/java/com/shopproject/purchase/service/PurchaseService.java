package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.dtos.PurchaseDto;
import org.springframework.data.domain.Page;

import java.util.UUID;


public interface PurchaseService {

    Page<PurchaseDto> getOrdersByUser(int pageNumber, int pageSize);

    ProductDto buy(UUID productId);

    GuaranteeDto refund(UUID purchaseId);
}
