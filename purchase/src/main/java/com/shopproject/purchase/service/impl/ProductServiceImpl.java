package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.exeptions.NoMoreProductsInStorageException;
import com.shopproject.purchase.exeptions.ProductException;
import com.shopproject.purchase.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final AuthServiceImpl authServiceImpl;
    private final RestTemplate restTemplate;


    @Override
    public ProductDto takeOne(UUID productId) throws NoMoreProductsInStorageException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authServiceImpl.getTokenByTechnicalUser());
        ResponseEntity<ProductDto> response;
        try {
            response = restTemplate.exchange(
                    "lb://PRODUCT/products/take/" + productId,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    ProductDto.class);
        } catch (Exception e){
            throw new RuntimeException("Ошибка при обращении к сервису товаров");
        }

        if (response.getStatusCode().isError()) {
            if (response.getStatusCode().is4xxClientError()) {
                throw new NoMoreProductsInStorageException();
            }
            throw new RuntimeException("Ошибка при обращении к сервису товаров");
        }

        return response.getBody();
    }

    @Override
    public void putOne(UUID productId) throws ProductException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authServiceImpl.getTokenByTechnicalUser());
        ResponseEntity<ProductDto> response = null;
        try {
            response = restTemplate.exchange(
                    "lb://PRODUCT/products/put/" + productId,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    ProductDto.class);
        } catch (Exception e){
            throw new ProductException("Ошибка при обращении к сервису товаров");
        }
        if (response.getStatusCode().isError()) {
            if (response.getStatusCode().is4xxClientError()) {
                throw new ProductException("Товар не возвращен на склад");
            }
            throw new ProductException("Ошибка при обращении к сервису товаров");
        }

    }

}
