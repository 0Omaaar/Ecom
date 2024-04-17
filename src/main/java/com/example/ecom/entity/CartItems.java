package com.example.ecom.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Data
public class CartItems {

    @Id @GeneratedValue
    private Long id;

    private Long price;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
}
