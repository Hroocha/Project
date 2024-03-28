package com.shopproject.user.dtos;

import lombok.Data;

@Data
public class RegistrationUserDTO {
    private String login;
    private String password;
    private String confirmPassword;
    private String name;
    private String mail;
}