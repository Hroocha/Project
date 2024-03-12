package com.shopproject.product.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private UUID id;
    @Column (name = "name")
    private String name;
    @Column (name = "price")
    private int price;
    @Column (name = "guarantee_period")
    private Timestamp guarantee_period;
    @Column (name = "picture")
    private byte[] picture;

    public Product(UUID id, String name, int price, Timestamp guarantee_period, byte[] picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.guarantee_period = guarantee_period;
        this.picture = picture;
    }
}
