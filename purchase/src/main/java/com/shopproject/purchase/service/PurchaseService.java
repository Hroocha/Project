package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.GuaranteeRequest;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.exeptions.AppError;
import com.shopproject.purchase.repository.PurchaseRepository;
import com.sun.tools.attach.AttachOperationFailedException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.shopproject.purchase.entities.Status.*;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    private final RequestService requestService;

    private final long time = 10000; // 10 секунд = 10000 миллисекунд

    public Page<Purchase> getOrdersByUser(int pageNumber) {
        UUID id = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("dateOfPurchase").ascending());
        return purchaseRepository.findByUserId(pageable, id);
    }

    //ГОТОВО
    public ResponseEntity<?> buy(UUID productId) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        BigDecimal price = requestService.takeProduct(productId);
        if (price != null) {
            Purchase purchase = new Purchase(userId, productId, price, java.time.LocalDateTime.now(), CREATE);
            purchaseRepository.save(purchase);
            return ResponseEntity.ok(new ProductDto(productId, price));
        } else { // если цена null, значит товара нет, и покупка не создается
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Товар закончился"), HttpStatus.NOT_FOUND);
        }
    }

//    ГОТОВО
    public ResponseEntity<?> refund(UUID purchaseId) throws AttachOperationFailedException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        checkUserByPurchase(purchaseId, userId);
        LocalDate validUntil = requestService.getGuaranteeByPurchase(purchaseId);
        if (validUntil != null) {
            LocalDate now = LocalDate.now();
            int comparisonResult = now.compareTo(validUntil);
            if (comparisonResult <= 0) {
                Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() ->
                        new NotFoundException("Покупка не найдена"));
                requestService.stopGuarantee(purchaseId);
                purchase.setStatus(PAYMENT_ERROR);
                purchase.setComment("refund");
                purchaseRepository.save(purchase);
                return ResponseEntity.ok(new GuaranteeDto(purchaseId, validUntil));// точно ли нужно это возвращать?
            } else {
                return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Гарантия истекла"),
                        HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), "Гарантия не найдена"),
                    HttpStatus.NOT_FOUND);
        }
    }

    private void checkUserByPurchase(UUID purchaseId, UUID userId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() ->
                new NotFoundException("Покупка не найдена"));
        if (!userId.equals(purchase.getUserId())) {
            throw new NotFoundException("Покупка не найдена у данного полдьзователя");
        }
    }

    @Retryable(value = {Exception.class}, maxAttempts = 10, backoff = @Backoff(delay = 1000))
    public void retrySetGuarantee(GuaranteeRequest guarantee) throws AttachOperationFailedException {
        requestService.setGuarantee(guarantee);
    }

    //ГОТОВО
    @Scheduled(fixedRate = time)
    @Transactional
    public void makeGuarantee() throws AttachOperationFailedException {
        Purchase purchase = purchaseRepository.findFirstByStatusOrderByDateOfPurchase(PAID).orElse(null);
        if (purchase != null) {
            Integer validUntil = requestService.getGuaranteePeriod(purchase.getProductId());
            if (validUntil != null) {
                GuaranteeRequest guarantee = new GuaranteeRequest(purchase.getId(), validUntil);
                retrySetGuarantee(guarantee);
                purchase.setStatus(PURCHASED);
                purchase.setComment("");
            } else {
                purchase.setStatus(GUARANTEE_ERROR);
                purchase.setComment("guarantee error");
            }
            purchaseRepository.save(purchase);
        }
    }

    // ГОТОВО
    @Scheduled(fixedRate = time)
    @Transactional
    public void putProduct() throws AttachOperationFailedException {
        Purchase purchase = purchaseRepository.findFirstByStatusOrderByDateOfPurchase(PRODUCT_ERROR).orElse(null);
        if (purchase != null) {
            requestService.putProduct(purchase.getProductId());
            if (purchase.getComment().equals("refund")) {
                purchase.setStatus(REFUND);
            } else {
                purchase.setStatus(PURCHASE_ERROR);
            }
            purchaseRepository.save(purchase);
        }
    }


    // ГОТОВО
    @Scheduled(fixedRate = time)
    @Transactional
    public void pay() { // 2 оплата
        Purchase purchase = purchaseRepository.findFirstByStatusOrderByDateOfPurchase(CREATE).orElse(null);
        if (purchase != null) {
//            purchase.getPrice(); // было бы нужно для оплаты
            Random rand = new Random();
            int randomNumber = rand.nextInt(10000) + 1;
//            randomNumber = 1; // проверка
            if (randomNumber == 1) {
                purchase.setStatus(PAYMENT_ERROR); //
                purchase.setComment("payment error");
            } else {
                purchase.setStatus(PAID);
            }
            purchaseRepository.save(purchase);
        }
    }

    // ГОТОВО
    @Scheduled(fixedRate = time)
    @Transactional
    public void refundMoney() {
        Purchase purchase = purchaseRepository.
                findFirstByStatusOrStatusOrderByDateOfPurchase(PAYMENT_ERROR, GUARANTEE_ERROR).orElse(null);
        if (purchase != null) {
//            purchase.getPrice(); // было бы нужно для возврата средств
            purchase.setStatus(PRODUCT_ERROR);
            purchaseRepository.save(purchase);
        }
    }


}
