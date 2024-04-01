package com.shopproject.report.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ReportService {

    public ResponseEntity<?> getSales(LocalDate dateFrom, LocalDate dateTo){

        return null;
    }

    public ResponseEntity<?> getSalesByUserId (UUID userId, LocalDate dateFrom, LocalDate dateTo){
        return null;
    }

    public ResponseEntity<?> getAverageBill(LocalDate dateFrom, LocalDate dateTo){
        return null;
    }

    public ResponseEntity<?> getAverageBillByUserId(UUID userId, LocalDate dateFrom, LocalDate dateTo){
        return null;
    }

}
