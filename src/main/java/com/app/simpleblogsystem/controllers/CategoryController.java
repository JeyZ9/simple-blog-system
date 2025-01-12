package com.app.simpleblogsystem.controllers;

import com.app.simpleblogsystem.config.ApiResponse;
import com.app.simpleblogsystem.config.MessageStrings;
import com.app.simpleblogsystem.models.Category;
import com.app.simpleblogsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCategory() {
        List<Category> categoryList = categoryService.getCategory();
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, categoryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        ApiResponse<Object> response = new ApiResponse<>(200, MessageStrings.SUCCESS_GET, category);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createCategory(@RequestBody Category category) {
        Category addCategory = categoryService.createCategory(category);
        ApiResponse<Object> response = new ApiResponse<>(201, MessageStrings.SUCCESS_CREATE, addCategory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category newCategory = categoryService.updateCategory(id, category);
        ApiResponse<Object> response = new ApiResponse<>(201, MessageStrings.SUCCESS_UPDATE, newCategory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> removeCategory(@PathVariable Long id) {
        Category category = categoryService.removeCategory(id);
        ApiResponse<Object> response = new ApiResponse<>(204, MessageStrings.SUCCESS_DELETE, category);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
