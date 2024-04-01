package com.shopproject.purchase.controller;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.service.AuthService;
import com.shopproject.purchase.service.PurchaseService;
import com.shopproject.purchase.service.RequestService;
import com.sun.tools.attach.AttachOperationFailedException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@EnableScheduling
@EnableRetry
@RequiredArgsConstructor
@Tag(name = "main_methods")
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping("/orders")
    public Page<Purchase> showByID(@RequestParam(value = "page") int page) {
        return purchaseService.getOrdersByUser(page);
    }

    @PostMapping("/purchase/{id}")
    public ResponseEntity<?> purchase(@PathVariable(value = "id") UUID productId) {
        return purchaseService.buy(productId);
    }

    @PostMapping("/refund/{id}")
    public ResponseEntity<?> refund(@PathVariable(value = "id") UUID purchaseId) throws AttachOperationFailedException {
        return purchaseService.refund(purchaseId);
    }

    @GetMapping("/purchase/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc(){
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }

}
