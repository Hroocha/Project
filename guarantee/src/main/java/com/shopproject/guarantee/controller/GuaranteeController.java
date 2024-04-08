package com.shopproject.guarantee.controller;

import com.shopproject.guarantee.dto.GuaranteeRequest;
import com.shopproject.guarantee.service.GuaranteeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "main_methods")
public class GuaranteeController {
    private final GuaranteeService guaranteeService;

    @PostMapping ("/guarantee/stop/{purchase_id}")
    public ResponseEntity<?> stopGuarantee (@PathVariable(value = "purchase_id") UUID purchaseId){
        return ResponseEntity.ok(guaranteeService.stopGuarantee(purchaseId));
    }

    @PostMapping ("/guarantee/set")
    public ResponseEntity<?> setGuarantee (@RequestBody GuaranteeRequest guarantee){

        return ResponseEntity.ok(guaranteeService.setGuarantee(guarantee.getPurchaseId(), guarantee.getGuaranteePeriod()));
    }

    @GetMapping("/guarantee/{purchase_id}")
    public ResponseEntity<?> getGuarantee (@PathVariable(value = "purchase_id") UUID purchaseId){
        return ResponseEntity.ok(guaranteeService.getGuarantee(purchaseId));
    }

    @GetMapping("/guarantee/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc(){
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }



}
