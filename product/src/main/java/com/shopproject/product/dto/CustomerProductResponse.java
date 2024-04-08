package com.shopproject.product.dto;

import com.shopproject.product.entities.Product;
import com.shopproject.product.entities.Warehouse;
import com.shopproject.product.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CustomerProductResponse implements Function<Product, ProductResponse> {

    private final WarehouseRepository warehouse;

    @Override
    public ProductResponse apply(Product product) {
        Optional<Warehouse> w = warehouse.findById(product.getId());
        String quantity = null;
        if (w.isPresent()){
            quantity = w.get().getQuantity().toString();
        }
        if(quantity == null || quantity.equals("0") || quantity.equals("null")){
            quantity = "Нет на складе";
        } else {
            quantity = "В наличии: " + quantity;
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
