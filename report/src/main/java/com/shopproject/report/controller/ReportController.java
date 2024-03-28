package com.shopproject.report.controller;

import com.shopproject.report.entity.Report;
import com.shopproject.report.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "main_methods")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("statistics/sales")
    public Report sales(@RequestParam(value = "date_from") LocalDate date_from,
                        @RequestParam(value = "date_to") LocalDate date_to) {
        return null;
    }

    @GetMapping("statistics/sales{user_id}")
    public Report salesOnID(@PathVariable(value = "user_id") UUID user_id,
                            @RequestParam(value = "date_from") LocalDate date_from,
                            @RequestParam(value = "date_to") LocalDate date_to) {
        return null;
    }

    @GetMapping("statistics/average_bill")
    public Report averageBill(@RequestParam(value = "date_from") LocalDate date_from,
                              @RequestParam(value = "date_to") LocalDate date_to) {
        return null;
    }

    @GetMapping("statistics/average_bill/{user_id}")
    public Report averageBillOnID(@PathVariable(value = "user_id") UUID user_id,
                                  @RequestParam(value = "date_from") LocalDate date_from,
                                  @RequestParam(value = "date_to") LocalDate date_to) {
        return null;
    }

    @GetMapping("/test")
    public String refund (){
        return "It work";
    }

}
