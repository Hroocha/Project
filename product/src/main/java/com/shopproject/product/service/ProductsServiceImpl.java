package com.shopproject.product.service;

import com.shopproject.product.dto.ProductDto;
import com.shopproject.product.dto.ProductResponse;
import com.shopproject.product.mapper.ProductResponseMapper;
import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.repository.ProductRepository;
import com.shopproject.product.repository.WarehouseRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsServiceImpl implements ProductsService{

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductResponseMapper productResponseMapper;

    @Override
    @Transactional
    public Page<ProductResponse> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        return productRepository.findAll(pageable).map(productResponseMapper);
    }

    @Override
    @Transactional
    public ProductResponse getById(UUID id) {
        return productRepository.findById(id)
                .map(productResponseMapper)
                .orElseThrow(() -> new NotFoundException(
                String.format("Продукт '%s' не найден", id))
        );
    }

    @Override
    @Transactional
    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 800, multiplier = 2))
    public ProductDto take(UUID id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        if (warehouse.getQuantity() <= 0) {
            throw new NotFoundException("Товар закончился");
        } else {
            warehouse.setQuantity(warehouse.getQuantity()-1);
            warehouseRepository.save(warehouse);
            Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                    String.format("Товар '%s' не найден", id))
            );
            return new ProductDto(product.getId(), product.getPrice(), product.getGuaranteePeriod());
        }
    }

    @Override
    @Transactional
    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 800, multiplier = 2))
    public ProductDto put(UUID id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        warehouse.setQuantity(warehouse.getQuantity()+1);
        warehouseRepository.save(warehouse);
        return new ProductDto(product.getId(), product.getPrice(), product.getGuaranteePeriod());
    }

}
