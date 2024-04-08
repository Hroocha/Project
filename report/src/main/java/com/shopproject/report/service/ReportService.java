package com.shopproject.report.service;

import com.shopproject.report.dtos.SalesRequest;
import com.shopproject.report.dtos.SalesResponse;
import com.shopproject.report.exeptions.AppError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final AuthService authService;
    private final RestTemplate restTemplate;


    public ResponseEntity<?> getSales(SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<SalesResponse> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8082/sales",
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    SalesResponse.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getSalesByProductId(UUID productId, SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<SalesResponse> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8082/sales/" + productId,
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    SalesResponse.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAverageBill(SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<Double> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8082/average_bill",
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    Double.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> getAverageBillByUserId(UUID userId, SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<Double> response = null;
        try {
            response = restTemplate.exchange(
                    "http://localhost:8082/average_bill/" + userId,
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    Double.class);
        } catch (ResourceAccessException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(Objects.requireNonNull(response.getBody()));
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "Ошибка при получении ответа"),
                    HttpStatus.NOT_FOUND);
        }
    }

}
