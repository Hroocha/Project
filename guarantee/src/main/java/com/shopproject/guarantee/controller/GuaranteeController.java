package com.shopproject.guarantee.controller;

import com.shopproject.guarantee.dto.GuaranteeDto;
import com.shopproject.guarantee.dto.GuaranteeRequest;
import com.shopproject.guarantee.entity.Guarantee;
import com.shopproject.guarantee.service.GuaranteeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "main_methods")
public class GuaranteeController {
    private final GuaranteeService guaranteeService;

    @Autowired
    public GuaranteeController(GuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    @PostMapping ("/guarantee/stop/{purchase_id}")
    public ResponseEntity<?> stopGuarantee (@PathVariable(value = "purchase_id") UUID purchaseId){
        return guaranteeService.stopGuarantee(purchaseId);
    }

    @PostMapping ("/guarantee/set")
    public ResponseEntity<?> setGuarantee (@RequestBody GuaranteeRequest guarantee){
        return guaranteeService.setGuarantee(guarantee.getPurchaseId(), guarantee.getValidInMonth());
    }

    @GetMapping("/guarantee/{purchase_id}")
    public ResponseEntity<?> getGuarantee (@PathVariable(value = "purchase_id") UUID purchaseId){
        return guaranteeService.getGuarantee(purchaseId);
    }


}
