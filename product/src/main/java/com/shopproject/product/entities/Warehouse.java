package com.shopproject.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Version
    @Column (name = "quantity")
    private Integer quantity;


}
