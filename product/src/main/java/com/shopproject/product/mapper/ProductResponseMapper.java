package com.shopproject.product.mapper;

import com.shopproject.product.dto.ProductResponse;
import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductResponseMapper implements Function<Product, ProductResponse> {

    private final WarehouseRepository warehouse;

    @Override
    public ProductResponse apply(Product product) {
        UUID id = product.getId();
        Optional<Warehouse> w = warehouse.findById(id);
        Integer quantity = null;
        if (w.isPresent()){
            quantity = w.get().getQuantity();
        }
        if(quantity == null){
            quantity = 0;
        }
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getGuaranteePeriod(),
                product.getPicture(),
                quantity
        );
    }
}
