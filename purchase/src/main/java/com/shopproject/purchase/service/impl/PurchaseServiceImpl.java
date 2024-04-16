package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.*;
import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.exeptions.GuaranteeException;
import com.shopproject.purchase.exeptions.PaymentGatewayException;
import com.shopproject.purchase.exeptions.ProductException;
import com.shopproject.purchase.mapper.PurchaseDtoMapper;
import com.shopproject.purchase.repository.PurchaseRepository;
import com.shopproject.purchase.service.GuaranteeService;
import com.shopproject.purchase.service.PaymentService;
import com.shopproject.purchase.service.ProductService;
import com.shopproject.purchase.service.PurchaseService;

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
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;

    private final GuaranteeService guaranteeService;

    private final ProductService productService;

    private final PaymentService paymentService;

    private final PurchaseDtoMapper purchaseDtoMapper;

    private static final long SCHEDULERS_RESTART_TIME = 10000; // 10 секунд = 10000 миллисекунд


    @Override
    @Transactional
    public Page<PurchaseDto> getOrdersByUser(int pageNumber, int pageSize) {
        UUID id = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateOfPurchase").ascending());
        return purchaseRepository.findByUserId(pageable, id).map(purchaseDtoMapper);
    }

    @Override
    @Transactional
    public ProductDto buy(UUID productId) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        var product = productService.takeOne(productId);
        Purchase purchase = new Purchase(userId, productId, product.getPrice(),
                product.getGuaranteePeriod(), java.time.LocalDateTime.now(), WAITING_FOR_PAYMENT);
        purchaseRepository.save(purchase);
        return product;
    }

    @Override
    @Transactional
    public GuaranteeDto refund(UUID purchaseId) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        assertValidRefund(purchaseId, userId);

        GuaranteeDto guarantee = guaranteeService.getGuaranteeByPurchase(purchaseId);

        LocalDate now = LocalDate.now();
        if (now.compareTo(guarantee.getValidUntil()) <= 0) {
            purchaseRepository.findById(purchaseId).ifPresent(purchase -> {
                guaranteeService.stopGuarantee(purchaseId);
                purchase.setStatus(WAITING_FOR_REFUND_MONEY);
                purchase.setComment("Оформлен возврат товара");
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

    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void makeGuarantee() {
        var purchaseOptional = purchaseRepository
                .findFirstByStatusOrderByDateOfPurchase(WAITING_FOR_MAKE_GUARANTEE);
        if (purchaseOptional.isEmpty()) {
            return;
        }

        Purchase purchase = purchaseOptional.get();
        CreateGuaranteeRequest createGuaranteeRequest = new CreateGuaranteeRequest(
                purchase.getId(), purchase.getGuaranteePeriod());

        try {
            guaranteeService.createGuarantee(createGuaranteeRequest);
        } catch (Exception e) {
            purchase.setStatus(WAITING_FOR_REFUND_OF_PAYMENT);
            purchase.setComment("Ошибка при формировании гарантии");
            return;
        }

        purchase.setStatus(PURCHASED);
        purchase.setComment("");
    }

    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void putProductIfRefund() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(WAITING_FOR_RETURN_PRODUCT).ifPresent(purchase -> {
            try {
                productService.putOne(purchase.getProductId());
            } catch (ProductException e) {
                return;
            }
            purchase.setStatus(REFUND);
            purchaseRepository.save(purchase);
        });
    }

    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void putProductIfPurchaseError() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase((WAITING_FOR_PUT_BACK_PRODUCT)).ifPresent(purchase -> {
            try {
                productService.putOne(purchase.getProductId());
            } catch (ProductException e) {
                return;
            }
            purchase.setStatus(PURCHASE_ERROR);
        });
    }

    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void pay() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(WAITING_FOR_PAYMENT).ifPresent(purchase -> {
            try {
                paymentService.makePay(purchase.getPrice());
                purchase.setStatus(WAITING_FOR_MAKE_GUARANTEE);
            } catch (PaymentGatewayException e) {
                log.error("Не удалось провести оплату {}. Ошибка", purchase.getId(), e);
                purchase.setStatus(WAITING_FOR_PUT_BACK_PRODUCT);
                purchase.setComment("Ошибка оплаты");
            }
        });
    }


    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void refundMoneyIfPurchaseRefund() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(WAITING_FOR_REFUND_MONEY).ifPresent(purchase -> {
            try {
                paymentService.refundMoney(purchase.getPrice());
                purchase.setStatus(WAITING_FOR_RETURN_PRODUCT);
            } catch (PaymentGatewayException e) {
                log.error("Не удалось вернуть деньги для товара {}. Ошибка", purchase.getId(), e);
            }
        });
    }

    @Scheduled(fixedRate = SCHEDULERS_RESTART_TIME)
    @Transactional
    public void refundMoneyIfGetGuaranteeError() {
        purchaseRepository.findFirstByStatusOrderByDateOfPurchase(WAITING_FOR_REFUND_OF_PAYMENT).ifPresent(purchase -> {
            try {
                paymentService.refundMoney(purchase.getPrice());
                purchase.setStatus(WAITING_FOR_PUT_BACK_PRODUCT);
            } catch (PaymentGatewayException e) {
                log.error("Не удалось вернуть деньги для товара {}. Ошибка", purchase.getId(), e);
            }
        });
    }


}
