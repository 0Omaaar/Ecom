package com.example.ecom.controller.customer;

import com.example.ecom.dto.AddProductInCartDto;
import com.example.ecom.dto.OrderDto;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor @RequestMapping("/api/customer")
public class CartController {
    private final CartService cartService;
    private final OrderRepository orderRepository;

    @PostMapping("/cart")
    public ResponseEntity<?> addProductToCart(@RequestBody AddProductInCartDto addProductInCartDto){
        return cartService.addProductToCart(addProductInCartDto);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto = cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}
