package com.shopproject.purchase.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "product_id")
    private UUID productId;

    @Column (name = "price")
    private BigDecimal price;

    @Column (name = "guarantee_period")
    private Integer guaranteePeriod;

    @Column (name = "date_of_purchase")
    private LocalDateTime dateOfPurchase;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private Status status;

    @Column (name = "comment")
    private String comment = "";

    @Version
    @Column (name = "version")
    private Integer version;

    public Purchase(UUID userId, UUID productId, BigDecimal price, Integer guaranteePeriod,
                    LocalDateTime dateOfPurchase, Status status) {
        this.userId = userId;
        this.productId = productId;
        this.price = price;
        this.guaranteePeriod = guaranteePeriod;
        this.dateOfPurchase = dateOfPurchase;
        this.status = status;
        this.version = 0;
    }
}
