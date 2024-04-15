package com.example.ecom.services.admin.product;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto product) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String name);
}
