package com.shopproject.product.service;

import com.shopproject.product.dto.ProductDto;
import com.shopproject.product.dto.ProductResponse;
import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.mapper.ProductResponseMapper;
import com.shopproject.product.repository.ProductRepository;
import com.shopproject.product.repository.WarehouseRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductsServiceImpl implements ProductsService {

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
    public ProductDto take(UUID id) throws OptimisticLockException {
        Warehouse warehouse = getWarehouse(id);
        if (warehouse.getQuantity() <= 0) {
            throw new NotFoundException("Товар закончился");
        }
        warehouse.setQuantity(warehouse.getQuantity() - 1);
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        log.debug("Товар {} забрали со склада", product.getId());
        return new ProductDto(product.getId(), product.getPrice(), product.getGuaranteePeriod());
    }

    @Override
    @Transactional
    public ProductDto put(UUID id) {

        Warehouse warehouse = getWarehouse(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        warehouse.setQuantity(warehouse.getQuantity() + 1);
        return new ProductDto(product.getId(), product.getPrice(), product.getGuaranteePeriod());
    }

    private Warehouse getWarehouse(UUID id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
    }

}
