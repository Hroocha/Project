package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.SalesRequest;
import com.shopproject.purchase.dtos.SalesResponse;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.repository.PurchaseRepository;
import com.shopproject.purchase.service.StatisticService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.shopproject.purchase.entities.Status.PURCHASED;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final PurchaseRepository purchaseRepository;

    @Override
    @Transactional
    public SalesResponse getSales(LocalDateTime from, LocalDateTime to){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatus(from, to, PURCHASED);
        return new SalesResponse(purchases.size(), getTotal(purchases));
    }

    @Override
    @Transactional
    public SalesResponse getSalesByProductId(UUID productId, LocalDateTime from, LocalDateTime to){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatusAndProductIdEquals(
                from, to, PURCHASED, productId);
        return new SalesResponse(purchases.size(), getTotal(purchases));
    }

    @Override
    @Transactional
    public Double getAverageBill (SalesRequest salesRequest){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatus(
                salesRequest.getDateFrom(),salesRequest.getDateTo(), PURCHASED);
        return getAverage(purchases);
    }

    @Override
    @Transactional
    public Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest){
        List<Purchase> purchases = purchaseRepository.getAllByDateOfPurchaseBetweenAndStatusAndUserIdEquals(
                salesRequest.getDateFrom(),salesRequest.getDateTo(), PURCHASED, userId);
        return getAverage(purchases);
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
