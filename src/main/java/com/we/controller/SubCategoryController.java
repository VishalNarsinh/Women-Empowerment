package com.we.controller;

import com.we.dto.CustomMessage;
import com.we.dto.SubCategoryDto;
import com.we.service.SubCategoryService;
import com.we.service.impl.SubCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subcategory")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @GetMapping("/{subCategoryId}")
    public ResponseEntity<?> getSubCategories(@PathVariable long subCategoryId) {
        return ResponseEntity.ok(subCategoryService.findSubCategoryBySubCategoryId(subCategoryId));
    }

    @PostMapping("/save/{categoryId}")
    public ResponseEntity<?> saveSubCategory(@RequestBody SubCategoryDto subCategoryDto,@PathVariable long categoryId) {
        return ResponseEntity.ok(subCategoryService.saveSubCategory(subCategoryDto, categoryId));
    }

    @PutMapping("/update/{subCategoryId}/{categoryId}")
    public ResponseEntity<?> updateSubCategory(@RequestBody SubCategoryDto subCategoryDto,@PathVariable long subCategoryId,@PathVariable long categoryId) {
        return ResponseEntity.ok(subCategoryService.updateSubCategory(subCategoryDto, subCategoryId, categoryId));
    }

    @DeleteMapping("/delete/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable long subCategoryId) {
        subCategoryService.deleteSubCategory(subCategoryId);
        return ResponseEntity.ok(CustomMessage.builder().message("SubCategory deleted successfully").status("success").build());
    }
    
}
