package com.shopproject.purchase.controller;

import com.shopproject.purchase.dtos.PurchaseDto;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "main methods")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/orders")
    public Page<PurchaseDto> showByID(@RequestParam(value = "page") int page, @RequestParam(value = "page_size")int pageSize) {
        return purchaseService.getOrdersByUser(page, pageSize);
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@PathVariable(value = "id") UUID productId) {
        return ResponseEntity.ok(purchaseService.buy(productId));
    }

    @PostMapping("/refund/{id}")
    public ResponseEntity<?> refund(@PathVariable(value = "id") UUID purchaseId) {
        return ResponseEntity.ok(purchaseService.refund(purchaseId));
    }

    @Hidden
    @GetMapping("/purchase/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc(){
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }

}
