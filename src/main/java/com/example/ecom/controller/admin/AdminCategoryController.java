package com.example.ecom.controller.admin;

import com.example.ecom.dto.CategoryDto;
import com.example.ecom.entity.Category;
import com.example.ecom.services.admin.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("api/admin") @RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("category")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto){
        Category category = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
