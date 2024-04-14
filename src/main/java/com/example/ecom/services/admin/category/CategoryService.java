package com.example.ecom.services.admin.category;

import com.example.ecom.dto.CategoryDto;
import com.example.ecom.entity.Category;

import java.util.List;

public interface CategoryService {

    public Category createCategory(CategoryDto categoryDto);

    public List<Category> getAllCategories();
}
