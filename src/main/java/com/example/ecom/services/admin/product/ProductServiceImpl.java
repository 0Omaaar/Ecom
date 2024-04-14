package com.example.ecom.services.admin.product;

import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.Category;
import com.example.ecom.entity.Product;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImg(productDto.getImg().getBytes());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).toList();
    }
}
