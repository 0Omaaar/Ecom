package com.example.ecom.dto;

import com.example.ecom.entity.User;
import lombok.Data;

@Data
public class AddProductInCartDto {
    private Long userId;

    private Long productId;
}
