package com.example.ecom.services.customer;

import com.example.ecom.dto.ProductDetailDto;
import com.example.ecom.dto.ProductDto;
import com.example.ecom.entity.FAQ;
import com.example.ecom.entity.Product;
import com.example.ecom.entity.Review;
import com.example.ecom.repository.FAQRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    private final ReviewRepository reviewRepository;

    private final FAQRepository faqRepository;

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

    @Override
    public ProductDetailDto getProductDetailById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);
            List<Review> reviewList = reviewRepository.findAllByProductId(productId);

            ProductDetailDto productDetailDto = new ProductDetailDto();
            productDetailDto.setProductDto(optionalProduct.get().getDto());
            productDetailDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

            return productDetailDto;
        }
        return null;
    }

}
