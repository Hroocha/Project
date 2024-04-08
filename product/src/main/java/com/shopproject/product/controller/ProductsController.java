package com.shopproject.product.controller;


import com.shopproject.product.entities.Product;
import com.shopproject.product.service.ProductsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@EnableScheduling
@Tag(name = "main_methods")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/products")
    public Page<Product> showAll (@RequestParam(value = "page") int page, @RequestParam(value = "page_size") int pageSize){
        return productsService.getAll(page, pageSize);
    }
    @GetMapping("/products/{id}")
    public Product showOneProduct(@PathVariable(value = "id") UUID productId){
        return productsService.getById(productId);
    }
//    @GetMapping("/products/guarantee/{id}")
//    public ProductDto getGuarantee(@PathVariable(value = "id") UUID productId){
//        return productsService.getById(productId).getGuaranteePeriod();
//    }
    @PostMapping("/products/take/{id}")
    public ResponseEntity<?> takeProduct(@PathVariable(value = "id") UUID productId){
        return ResponseEntity.ok(productsService.take(productId));
    }
    @PostMapping("/products/put/{id}")
    public ResponseEntity<?> putProduct(@PathVariable(value = "id") UUID productId){
        return ResponseEntity.ok(productsService.put(productId));
    }

    @GetMapping("/product/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc(){
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }



}

