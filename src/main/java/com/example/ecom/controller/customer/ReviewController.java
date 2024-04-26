package com.example.ecom.controller.customer;

import com.example.ecom.dto.OrderedProductsResponseDto;
import com.example.ecom.dto.ReviewDto;
import com.example.ecom.services.customer.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController @RequestMapping("/api/customer")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductsResponseDto> getOrderedProductsDetailsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto createdReview = reviewService.giveReview(reviewDto);
        if(createdReview == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong !");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }
}
