package com.shopproject.purchase.service;

import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.GuaranteeRequest;
import com.shopproject.purchase.dtos.ProductDto;
import com.sun.tools.attach.AttachOperationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final AuthService authService;
    private final RestTemplate restTemplate;

    public BigDecimal takeProduct(UUID productId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                "http://localhost:8080/products/take/" + productId,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                ProductDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull(response.getBody()).getPrice();
        } else {
            return null;
        }
    }

    public void putProduct(UUID productId) throws AttachOperationFailedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<ProductDto> response = restTemplate.exchange(
                "http://localhost:8080/products/put/" + productId,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                ProductDto.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AttachOperationFailedException("Товар не возвращен на склад");
        }
    }

    public Integer getGuaranteePeriod(UUID product_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<Integer> response = restTemplate.exchange(
                "http://localhost:8080/products/guarantee/" + product_id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Integer.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull((response).getBody());
        } else {
            return null;
        }
    }

    public LocalDate getGuaranteeByPurchase(UUID purchaseId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = restTemplate.exchange(
                "http://localhost:8083/guarantee/" + purchaseId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GuaranteeDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Objects.requireNonNull((response).getBody()).getValidUntil();
        } else {
            return null;
        }
    }

    public void stopGuarantee(UUID purchaseId) throws AttachOperationFailedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = restTemplate.exchange(
                "http://localhost:8083/guarantee/stop/" + purchaseId,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                GuaranteeDto.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AttachOperationFailedException("Гарантия не остановлена");
        }
    }

    public ResponseEntity<?> setGuarantee(GuaranteeRequest guarantee) throws AttachOperationFailedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = restTemplate.exchange(
                "http://localhost:8083/guarantee/set",
                HttpMethod.POST,
                new HttpEntity<>(guarantee, headers),
                GuaranteeDto.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(new GuaranteeDto(
                    Objects.requireNonNull(response.getBody()).getPurchaseId(),
                    response.getBody().getValidUntil()));
        } else {
            throw new AttachOperationFailedException(
                    "Гарантия не установлена");
        }
    }

}
