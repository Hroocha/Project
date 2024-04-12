package com.shopproject.product.controller;


import com.shopproject.product.dto.ProductResponse;
import com.shopproject.product.service.ProductsServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Tag(name = "main methods")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsServiceImpl productsService;

    @GetMapping("/products")
    public Page<ProductResponse> showAll (@RequestParam(value = "page") int page, @RequestParam(value = "page_size") int pageSize){
        return productsService.getAll(page, pageSize);
    }
    @GetMapping("/products/{id}")
    public ProductResponse showOneProduct(@PathVariable(value = "id") UUID productId){
        return productsService.getById(productId);
    }

    @PostMapping("/products/take/{id}")
    public ResponseEntity<?> takeProduct(@PathVariable(value = "id") UUID productId){
        return ResponseEntity.ok(productsService.take(productId));
    }
    @PostMapping("/products/put/{id}")
    public ResponseEntity<?> putProduct(@PathVariable(value = "id") UUID productId){
        return ResponseEntity.ok(productsService.put(productId));
    }

    @Hidden
    @GetMapping("/product/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc(){
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }



}

