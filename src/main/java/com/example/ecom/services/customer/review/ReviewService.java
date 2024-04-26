package com.example.ecom.services.customer.review;

import com.example.ecom.dto.OrderedProductsResponseDto;
import com.example.ecom.dto.ReviewDto;
import com.example.ecom.entity.Review;

import java.io.IOException;

public interface ReviewService {

    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
}
