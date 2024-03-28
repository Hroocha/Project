package com.shopproject.user.controllers;

import com.shopproject.user.dtos.JwtRequest;
import com.shopproject.user.dtos.JwtResponse;
import com.shopproject.user.dtos.RegistrationUserDTO;
import com.shopproject.user.dtos.UserDto;
import com.shopproject.user.entity.User;
import com.shopproject.user.exeptions.AppError;
import com.shopproject.user.service.UserService;
import com.shopproject.user.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "authentication_methods")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth") //log_in
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<> (new AppError(HttpStatus.UNAUTHORIZED.value(),
                    "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getLogin());
        UUID id = userService.getIdByLogin(authRequest.getLogin());
        String token = jwtTokenUtils.generateToken(userDetails, id);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?>  register (@RequestBody RegistrationUserDTO registrationUserDTO){
        if (!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userService.findByLogin(registrationUserDTO.getLogin()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.createNewUser(registrationUserDTO);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getName(), user.getMail()));
    }





}
