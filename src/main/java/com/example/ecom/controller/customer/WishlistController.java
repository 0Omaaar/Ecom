package com.example.ecom.controller.customer;

import com.example.ecom.dto.WishlistDto;
import com.example.ecom.services.customer.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/customer")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/wishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto){
        WishlistDto createdWishlist = wishlistService.addProductToWishList(wishlistDto);
        if(createdWishlist == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong !");
        }else{
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlist);
        }
    }

    @GetMapping("/wishlist/{userId}")
    public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(wishlistService.getWishlistByUserId(userId));
    }
}
