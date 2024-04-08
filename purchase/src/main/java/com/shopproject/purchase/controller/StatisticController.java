package com.shopproject.purchase.controller;


import com.shopproject.purchase.dtos.statisticsDto.SalesRequest;
import com.shopproject.purchase.service.impl.StatisticService;
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
        return statisticService.getSales(salesRequest.getDateFrom(),salesRequest.getDateTo());
    }

    @PostMapping("/sales/{product_id}")
    public ResponseEntity<?> SalesByProductId(@PathVariable(value = "product_id") UUID productId,
                                              @RequestBody SalesRequest salesRequest) {
        return statisticService.getSalesByProductId(productId, salesRequest.getDateFrom(), salesRequest.getDateTo());
    }

    @PostMapping("/average_bill")
    public ResponseEntity<?> averageBill(@RequestBody SalesRequest salesRequest) {
        return statisticService.getAverageBill(salesRequest);
    }

    @PostMapping("/average_bill/{user_id}")
    public ResponseEntity<?> averageBillByUserId(@PathVariable(value = "user_id") UUID userId,
                                                 @RequestBody SalesRequest salesRequest) {
        return statisticService.getAverageBillByUserId(userId, salesRequest);
    }


}
