package com.example.ecom.services.customer;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).toList();
    }

    @Override
    public List<ProductDto> searchProductByName(String name) {
        List<Product> productList = productRepository.findAllByNameContaining(name);
        return productList.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
