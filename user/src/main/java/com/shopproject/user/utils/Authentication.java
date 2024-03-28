package com.shopproject.user.utils;

import com.shopproject.user.dtos.JwtRequest;
import com.shopproject.user.exeptions.AppError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Authentication {
    private AuthenticationManager authenticationManager;

    public AppError authenticateUser(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль");
        }
        return null;
    }
}
