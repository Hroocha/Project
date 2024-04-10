package com.shopproject.product.service;

import com.shopproject.product.dto.ProductDto;
import com.shopproject.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductsService {

    Page<ProductResponse> getAll(int pageNumber, int pageSize);

    ProductResponse getById(UUID id);

    ProductDto take(UUID id);

    ProductDto put(UUID id);
}
