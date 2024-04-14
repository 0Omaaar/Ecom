package com.example.ecom.services.admin.category;

import com.example.ecom.dto.CategoryDto;
import com.example.ecom.entity.Category;
import com.example.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

//    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
