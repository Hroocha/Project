package com.shopproject.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name ="login")
    private String login;

    @Column(name ="password")
    private String password;

    @Column(name ="name")
    private String name;

    @Column(name ="mail")
    private String mail;

}
