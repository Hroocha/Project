package com.shopproject.product.service;


import com.shopproject.product.dto.ProductDto;
import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.exeptions.AppError;
import com.shopproject.product.repository.ProductRepository;
import com.shopproject.product.repository.WarehouseRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public Page<Product> getAll(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("name").ascending());
        return productRepository.findAll(pageable);
    }

    public Product getById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Продукт '%s' не найден", id))
        );
    }

    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 10, backoff = @Backoff(delay = 1000))
    public ResponseEntity<?> take(UUID id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        if (warehouse.getQuantity() <= 0) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Товар закончился"), HttpStatus.NOT_FOUND);
        } else {
            warehouse.setQuantity(warehouse.getQuantity()-1);
            warehouseRepository.save(warehouse);
            Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                    String.format("Товар '%s' не найден", id))
            );
            return ResponseEntity.ok(new ProductDto(product.getId(), product.getPrice()));
        }
    }
    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 10, backoff = @Backoff(delay = 1000))
    public ResponseEntity<?> put(UUID id) {
        Warehouse warehouse = warehouseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Товар '%s' не найден", id))
        );
        warehouse.setQuantity(warehouse.getQuantity()+1);
        warehouseRepository.save(warehouse);
        return ResponseEntity.ok(new ProductDto(product.getId(), product.getPrice()));
    }

    @Recover
    public ResponseEntity<?> recover(ObjectOptimisticLockingFailureException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.LOCKED.value(),
                "Ошибка при попытке изменить количество товара"),HttpStatus.LOCKED);
    }

//    @PostConstruct // тоже работает, но пока отколючила, лучше через компонент
//    public void incrementQuantity() {
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(() -> warehouseRepository.quantityPutTwo(), 0, 5, TimeUnit.MINUTES);
//    }

}
