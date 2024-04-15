package com.example.ecom.controller.admin;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.services.admin.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController @RequestMapping("api/admin") @RequiredArgsConstructor
public class AdminProductController {

    private final  ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {  //modelAttribute cause we
        // have multipart file in the request
        ProductDto productDto1 = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> productDtos = productService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name){
        List<ProductDto> productDtos = productService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }
}
