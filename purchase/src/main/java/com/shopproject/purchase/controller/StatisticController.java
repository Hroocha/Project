package com.shopproject.purchase.controller;

import com.shopproject.purchase.dtos.SalesRequest;
import com.shopproject.purchase.service.StatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "statistic methods")
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping("/sales")
    public ResponseEntity<?> Sales(@RequestBody SalesRequest salesRequest) {
        return ResponseEntity.ok(statisticService.getSales(salesRequest.getDateFrom(),salesRequest.getDateTo()));
    }

    @PostMapping("/sales/{product_id}")
    public ResponseEntity<?> SalesByProductId(@PathVariable(value = "product_id") UUID productId,
                                              @RequestBody SalesRequest salesRequest) {
        return ResponseEntity.ok(statisticService.getSalesByProductId(
                productId, salesRequest.getDateFrom(), salesRequest.getDateTo()));
    }

    @PostMapping("/average_bill")
    public ResponseEntity<?> averageBill(@RequestBody SalesRequest salesRequest) {
        return ResponseEntity.ok(statisticService.getAverageBill(salesRequest));
    }

    @PostMapping("/average_bill/{user_id}")
    public ResponseEntity<?> averageBillByUserId(@PathVariable(value = "user_id") UUID userId,
                                                 @RequestBody SalesRequest salesRequest) {
        return ResponseEntity.ok(statisticService.getAverageBillByUserId(userId, salesRequest));
    }


}
