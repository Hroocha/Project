package com.shopproject.user.controllers;

import com.shopproject.user.dtos.JwtRequest;
import com.shopproject.user.dtos.RegistrationUserDTO;
import com.shopproject.user.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "authentication methods")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?>  register (@RequestBody RegistrationUserDTO registrationUserDTO){
        return authService.createNewUser(registrationUserDTO);
    }

}
