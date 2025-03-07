package com.we.controller;

import com.we.dto.CategoryDto;
import com.we.dto.CustomMessage;
import com.we.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryDto));
    }

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<?> getSubCategory(@PathVariable long subCategoryId) {
        return ResponseEntity.ok(categoryService.findCategoryById(subCategoryId));
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable long categoryId) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto,categoryId));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryService.findCategoryById(categoryId));
        return ResponseEntity.ok(CustomMessage.builder().message("Category deleted successfully").status("success").build());
    }
}
