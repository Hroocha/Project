package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.statisticsDto.SalesRequest;
import com.shopproject.purchase.dtos.statisticsDto.SalesResponse;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shopproject.purchase.entities.Status.PURCHASED;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final PurchaseRepository purchaseRepository;

    @Transactional
    public ResponseEntity<?> getSales(LocalDateTime from, LocalDateTime to){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatus(from, to, PURCHASED);
        return ResponseEntity.ok(new SalesResponse("всего покупок", purchases.size(), getTotal(purchases)));
    }
    @Transactional
    public ResponseEntity<?> getSalesByProductId(UUID productId, LocalDateTime from, LocalDateTime to){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatusAndProductIdEquals(
                from, to, PURCHASED, productId);
        return ResponseEntity.ok(new SalesResponse( productId.toString(), purchases.size(), getTotal(purchases)));
    }
    @Transactional
    public ResponseEntity<?> getAverageBill (SalesRequest salesRequest){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatus(
                salesRequest.getDateFrom(),salesRequest.getDateTo(), PURCHASED);
        return ResponseEntity.ok(getAverage(purchases));
    }
    @Transactional
    public ResponseEntity<?> getAverageBillByUserId(UUID userId, SalesRequest salesRequest){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatusAndUserIdEquals(
                salesRequest.getDateFrom(),salesRequest.getDateTo(), PURCHASED, userId);
        return ResponseEntity.ok(getAverage(purchases));
    }


    private double getAverage(List<Purchase> list){
        List<BigDecimal> price = new ArrayList<>();
        list.forEach(purchase -> price.add(purchase.getPrice()));
        return price.stream().mapToDouble(BigDecimal::doubleValue)
                .average()
                .orElse(0.0);
    }

    private double getTotal(List<Purchase> list){
        List<BigDecimal> price = new ArrayList<>();
        list.forEach(purchase -> price.add(purchase.getPrice()));
        return price.stream().mapToDouble(BigDecimal::doubleValue).sum();
    }

}
