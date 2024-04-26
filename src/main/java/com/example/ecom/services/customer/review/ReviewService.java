package com.example.ecom.services.customer.review;

import com.example.ecom.dto.OrderedProductsResponseDto;

public interface ReviewService {

    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
}
