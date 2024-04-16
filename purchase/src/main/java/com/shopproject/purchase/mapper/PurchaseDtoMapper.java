package com.shopproject.purchase.mapper;

import com.shopproject.purchase.dtos.PurchaseDto;
import com.shopproject.purchase.entities.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PurchaseDtoMapper implements Function<Purchase, PurchaseDto> {

    @Override
    public PurchaseDto apply(Purchase purchase) {
        return new PurchaseDto(
                purchase.getId(),
                purchase.getProductId(),
                purchase.getPrice(),
                purchase.getGuaranteePeriod(),
                LocalDate.from(purchase.getDateOfPurchase()),
                purchase.getStatus(),
                purchase.getComment()
        );
    }
}
