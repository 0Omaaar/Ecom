package com.example.ecom.services.customer;

import com.example.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> searchProductByName(String name);
}
