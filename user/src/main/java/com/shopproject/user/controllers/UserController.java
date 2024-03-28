package com.shopproject.user.controllers;

import com.shopproject.user.dtos.UserDto;
import com.shopproject.user.entity.User;
import com.shopproject.user.exeptions.AppError;
import com.shopproject.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Tag(name = "user_methods")
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserDto userProfile () {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", login))
        );
        return new UserDto(user.getId(), user.getName(), user.getMail());
    }

}
