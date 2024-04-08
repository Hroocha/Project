package com.shopproject.purchase.service.impl;

import com.shopproject.purchase.dtos.CreateGuaranteeRequest;
import com.shopproject.purchase.dtos.GuaranteeDto;
import com.shopproject.purchase.dtos.ProductDto;
import com.shopproject.purchase.exeptions.GuaranteeException;
import com.shopproject.purchase.exeptions.NoMoreProductsInStorageException;
import com.shopproject.purchase.service.GuaranteeService;
import com.sun.tools.attach.AttachOperationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GuaranteeServiceImpl implements GuaranteeService {

    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Retryable(value = {GuaranteeException.class}, maxAttempts = 10, backoff = @Backoff(delay = 800, multiplier = 2))
    @Override
    public void createGuarantee(CreateGuaranteeRequest guarantee) throws GuaranteeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8083/guarantee/set",
                    HttpMethod.POST,
                    new HttpEntity<>(guarantee, headers),
                    GuaranteeDto.class);
        } catch (ResourceAccessException e) {
            throw new GuaranteeException("Ошибка при создании гарантии");
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GuaranteeException("Ошибка при создании гарантии");
        }
    }

    @Retryable(value = {GuaranteeException.class}, maxAttempts = 10, backoff = @Backoff(delay = 800, multiplier = 2))
    @Override
    public void stopGuarantee(UUID purchaseId) throws GuaranteeException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8083/guarantee/stop/" + purchaseId,
                    HttpMethod.POST,
                    new HttpEntity<>(headers),
                    GuaranteeDto.class);
        } catch (ResourceAccessException e) {
            throw new GuaranteeException("Ошибка при получении запроса от сервиса гарантий");
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GuaranteeException("Ошибка при попытке остановить гарантию");
        }
    }


    @Override
    public GuaranteeDto getGuaranteeByPurchase(UUID purchaseId) throws GuaranteeException {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<GuaranteeDto> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8083/guarantee/" + purchaseId,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    GuaranteeDto.class);
        } catch (ResourceAccessException e) {
            throw new GuaranteeException ("Ошибка при получении запроса от сервиса гарантий");
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new GuaranteeException ("Не удалось найти гарантию");
        }
    }


}
