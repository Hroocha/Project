package com.shopproject.guarantee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guarantees")
public class Guarantee {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "purchase_id")
    private UUID purchaseId;

    @Column (name = "valid_until")
    private LocalDate validUntil;

    @Version
    @Column(name = "version")
    private Integer version;

    public Guarantee(UUID purchaseId, LocalDate validUntil) {
        this.purchaseId = purchaseId;
        this.validUntil = validUntil;
        this.version = 0;
    }
}
