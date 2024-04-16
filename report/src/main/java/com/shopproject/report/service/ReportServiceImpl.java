package com.shopproject.report.service;

import com.shopproject.report.dtos.SalesRequest;
import com.shopproject.report.dtos.SalesResponse;
import com.shopproject.report.exeptions.ReportException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final AuthService authService;
    private final RestTemplate restTemplate;

    @Override
    public SalesResponse getSales(SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<SalesResponse> response = null;
        try {
            response = restTemplate.exchange(
                    "lb://PURCHASE/sales",
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    SalesResponse.class);
        } catch (ResourceAccessException e) {
            throw new ReportException("Ошибка при получении ответа");
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
           throw new ReportException("Ошибка при получении ответа");
        }
    }

    @Override
    public SalesResponse getSalesByProductId(UUID productId, SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<SalesResponse> response = null;
        try {
            response = restTemplate.exchange(
                    "lb://PURCHASE/sales/" + productId,
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    SalesResponse.class);
        } catch (Exception e) {
            throw new ReportException("Ошибка при получении ответа");
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new ReportException("Ошибка при получении ответа");
        }
    }

    @Override
    public Double getAverageBill(SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<Double> response = null;
        try {
            response = restTemplate.exchange(
                    "lb://PURCHASE/average_bill",
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    Double.class);
        } catch (Exception e) {
            throw new ReportException("Ошибка при получении ответа");
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new ReportException("Ошибка при получении ответа");
        }
    }

    @Override
    public Double getAverageBillByUserId(UUID userId, SalesRequest salesRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getTokenByTechnicalUser());
        ResponseEntity<Double> response = null;
        try {
            response = restTemplate.exchange(
                    "lb://PURCHASE/average_bill/" + userId,
                    HttpMethod.POST,
                    new HttpEntity<>(salesRequest, headers),
                    Double.class);
        } catch (Exception e) {
            throw new ReportException("Ошибка при получении ответа");
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new ReportException("Ошибка при получении ответа");
        }
    }

}
