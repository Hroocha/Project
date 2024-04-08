package com.shopproject.report.service;

import com.shopproject.report.dtos.JwtRequest;
import com.shopproject.report.dtos.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RestTemplate restTemplate;

    public String getTokenByTechnicalUser() {
        JwtRequest request = new JwtRequest("technicalUser", "123");
        ResponseEntity<JwtResponse> response = restTemplate.exchange(
                "http://localhost:8081/auth",
                HttpMethod.POST,
                new HttpEntity<>(request),
                JwtResponse.class
        );
        return Objects.requireNonNull(response.getBody()).getToken();
    }
}
