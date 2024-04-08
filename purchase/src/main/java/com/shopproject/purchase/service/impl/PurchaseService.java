package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.CreateGuaranteeRequest;
import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.entities.Status;
import com.shopproject.purchase.exeptions.GuaranteeException;
import com.shopproject.purchase.exeptions.ProductException;
import com.shopproject.purchase.repository.PurchaseRepository;
import com.shopproject.purchase.service.GuaranteeService;
import com.shopproject.purchase.service.PaymentService;
import com.shopproject.purchase.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static com.shopproject.purchase.entities.Status.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    private final GuaranteeService guaranteeService;

    private final ProductService productService;

    private final PaymentService paymentService;

    private final long SchedulersRestartTime = 10000; // 10 секунд = 10000 миллисекунд


    @Transactional
    public Page<Purchase> getOrdersByUser(int pageNumber, int pageSize) {
        UUID id = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateOfPurchase").ascending());
        return purchaseRepository.findByUserId(pageable, id);
    }

    @Transactional
    public ProductDto buy(UUID productId) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        var product = productService.takeOne(productId);
        Purchase purchase = new Purchase(userId, productId, product.getPrice(),
                product.getGuaranteePeriod(), java.time.LocalDateTime.now(), CREATE);
        purchaseRepository.save(purchase);
        return product;
    }


    @Transactional
    public GuaranteeDto refund(UUID purchaseId) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        assertValidRefund(purchaseId, userId);

        GuaranteeDto guarantee = guaranteeService.getGuaranteeByPurchase(purchaseId);

        LocalDate now = LocalDate.now();
        if (now.compareTo(guarantee.getValidUntil()) <= 0) {
            purchaseRepository.findById(purchaseId).ifPresent(purchase -> {
                guaranteeService.stopGuarantee(purchaseId);
                purchase.setStatus(REFUND_MONEY);
                purchase.setComment("Оформлен возврат товара");
                purchaseRepository.save(purchase);
            });
            return guarantee;
        } else {
            throw new GuaranteeException("Гарантия истекла");
        }
    }

    private void assertValidRefund(UUID purchaseId, UUID userId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() ->
                new IllegalArgumentException("Покупка не найдена"));
        if (!userId.equals(purchase.getUserId())) {
            throw new IllegalArgumentException("Покупка не найдена у данного пользователя");
        } else if (!purchase.getStatus().equals(PURCHASED)) {
            throw new IllegalArgumentException("Покупка находится не в статусе 'Куплено' ");
        }
    }

    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void makeGuarantee() {
        var purchaseOptional = purchaseRepository.findFirstByStatusOrderByDateOfPurchase(PAID);
        if (purchaseOptional.isEmpty()) {
            return;
        }

        Purchase purchase = purchaseOptional.get();
        CreateGuaranteeRequest createGuaranteeRequest = new CreateGuaranteeRequest(purchase.getId(), purchase.getGuaranteePeriod());

        try {
            guaranteeService.createGuarantee(createGuaranteeRequest);
        } catch (Exception e) {
            purchase.setStatus(GUARANTEE_ERROR);
            purchase.setComment("Ошибка при формировании гарантии");
            return;
        }

        purchase.setStatus(PURCHASED);
        purchase.setComment("");
    }

    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void putProductIfRefund() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(PRODUCT_RETURN).ifPresent(purchase -> {
            try {
                productService.putOne(purchase.getProductId());
            } catch (ProductException e) {
                return;
            }
            purchase.setStatus(REFUND);
            purchaseRepository.save(purchase);
        });
    }

    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void putProductIfPurchaseError() {
        Status[] statuses = {PUT_PRODUCT, PAYMENT_ERROR};
        purchaseRepository.findFirstByStatusInOrderByDateOfPurchase(statuses).ifPresent(purchase -> {
            try {
                productService.putOne(purchase.getProductId());
            } catch (ProductException e) {
                return;
            }
            purchase.setStatus(PURCHASE_ERROR);
            purchaseRepository.save(purchase);
        });
    }

    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void pay() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(CREATE).ifPresent(purchase -> {
            if(paymentService.makePay(purchase.getPrice())){
                purchase.setStatus(PAID);
            } else {
                purchase.setStatus(PAYMENT_ERROR);
                purchase.setComment("Ошибка оплаты");
            }
            purchaseRepository.save(purchase);
        });
    }


    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void refundMoneyIfPurchaseRefund() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(REFUND_MONEY).ifPresent(purchase -> {
            if (paymentService.refundMoney(purchase.getPrice())) {
                purchase.setStatus(PRODUCT_RETURN);
                purchaseRepository.save(purchase);
            }
        });

    }

    @Scheduled(fixedRate = SchedulersRestartTime)
    @Transactional
    public void refundMoneyIfGetGuaranteeError() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(GUARANTEE_ERROR).ifPresent(purchase -> {
            if (paymentService.refundMoney(purchase.getPrice())) {
                purchase.setStatus(PUT_PRODUCT);
                purchaseRepository.save(purchase);
            }
        });

    }


}
