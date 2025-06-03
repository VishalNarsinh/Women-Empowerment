package com.lms.controller;

import com.lms.dto.CustomMessage;
import com.lms.dto.SubCategoryDto;
import com.lms.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<?> getSubCategories(@PathVariable long subCategoryId) {
        return ResponseEntity.ok(subCategoryService.findSubCategoryBySubCategoryId(subCategoryId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getSubCategoriesByCategory(@PathVariable long categoryId) {
        return ResponseEntity.ok(subCategoryService.findSubCategoriesByCategoryId(categoryId));
    }

    @PostMapping("/")
    public ResponseEntity<?> saveSubCategory(@RequestBody SubCategoryDto subCategoryDto) {
        return ResponseEntity.ok(subCategoryService.saveSubCategory(subCategoryDto));
    }

    @PutMapping("/{subCategoryId}")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategoryDto subCategoryDto,@PathVariable long subCategoryId) {
        return ResponseEntity.ok(subCategoryService.updateSubCategory(subCategoryDto, subCategoryId));
    }

    @DeleteMapping("/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.ok(CustomMessage.builder().message("Subcategory deleted successfully").status("success").build());
    }
    
}
