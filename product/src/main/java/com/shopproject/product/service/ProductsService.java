package com.shopproject.product.service;


import com.shopproject.product.dto.ProductDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public Page<Product> getAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        return productRepository.findAll(pageable);
    }

    public Product getById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Продукт '%s' не найден", id))
        );
    }

    @Transactional
//    @Retryable(maxAttempts = 10)
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

    @Transactional
//    @Retryable(maxAttempts = 10)
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

//    @Recover
//    public ResponseEntity<?> recover(ObjectOptimisticLockingFailureException e) {
//        return new ResponseEntity<>(new AppError(HttpStatus.LOCKED.value(),
//                "Ошибка при попытке изменить количество товара"),HttpStatus.LOCKED);
//    }

//    @PostConstruct // тоже работает, но пока отколючила, лучше через компонент
//    public void incrementQuantity() {
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        executor.scheduleAtFixedRate(() -> warehouseRepository.quantityPutTwo(), 0, 5, TimeUnit.MINUTES);
//    }

}
