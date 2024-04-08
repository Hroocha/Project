package com.shopproject.report.controller;

import com.shopproject.report.dtos.SalesRequest;
import com.shopproject.report.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "main_methods")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("statistics/sales")
    public ResponseEntity<?> sales(@RequestParam(value = "date_from") LocalDate dateFrom,
                                   @RequestParam(value = "date_to") LocalDate dateTo) {
        LocalDateTime from = LocalDateTime.of(dateFrom, LocalTime.of(0,0));
        LocalDateTime to = LocalDateTime.of(dateTo, LocalTime.of(0,0));
        SalesRequest request = new SalesRequest(from, to);
        return reportService.getSales(request);
    }

    @GetMapping("statistics/sales/{product_id}")
    public ResponseEntity<?> SalesByProductId(@PathVariable(value = "product_id") UUID productId,
                                              @RequestParam(value = "date_from") LocalDate dateFrom,
                                              @RequestParam(value = "date_to") LocalDate dateTo) {
        LocalDateTime from = LocalDateTime.of(dateFrom, LocalTime.of(0,0));
        LocalDateTime to = LocalDateTime.of(dateTo, LocalTime.of(0,0));
        return reportService.getSalesByProductId(productId, new SalesRequest(from, to));
    }

    @GetMapping("statistics/average_bill")
    public ResponseEntity<?> averageBill(@RequestParam(value = "date_from") LocalDate dateFrom,
                                         @RequestParam(value = "date_to") LocalDate dateTo) {
        LocalDateTime from = LocalDateTime.of(dateFrom, LocalTime.of(0,0));
        LocalDateTime to = LocalDateTime.of(dateTo, LocalTime.of(23,59));
        return reportService.getAverageBill(new SalesRequest(from, to));
    }

    @GetMapping("statistics/average_bill/{user_id}")
    public ResponseEntity<?> averageBillByUserId(@PathVariable(value = "user_id") UUID userId,
                                                 @RequestParam(value = "date_from") LocalDate dateFrom,
                                                 @RequestParam(value = "date_to") LocalDate dateTo) {
        LocalDateTime from = LocalDateTime.of(dateFrom, LocalTime.of(0,0));
        LocalDateTime to = LocalDateTime.of(dateTo, LocalTime.of(0,0));
        return reportService.getAverageBillByUserId(userId, new SalesRequest(from, to));
    }

    @GetMapping("/report/api-docs")
    public SpringDocConfigProperties.ApiDocs.OpenApiVersion[] doc() {
        return SpringDocConfigProperties.ApiDocs.OpenApiVersion.values();
    }

}
