package com.example.ecom.controller.customer;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.services.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("api/customer") @RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDtos = customerProductService.searchProductByName(name);
        return ResponseEntity.ok(productDtos);
    }
}
