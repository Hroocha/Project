package com.shopproject.product.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column (name = "quantity")
    private Integer quantity;

    @Version
    @Column(name = "version")
    @Builder.Default
    private Integer version = 0;

    public Warehouse(Integer quantity) {
        this.quantity = quantity;
        this.version = 0;
    }
}
