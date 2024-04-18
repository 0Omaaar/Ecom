package com.example.ecom.dto;

import com.example.ecom.entity.CartItems;
import com.example.ecom.entity.User;
import com.example.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;  //amount of the order after applying coupon..

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;  //amount of the product

    private Long discount;

    private UUID trackingId;

    private String userName;

    private List<CartItemsDto> cartItems;
}
