package com.lms.service;

import com.lms.dto.CategoryDto;

import java.util.List;

public interface CategoryService {


    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,long categoryId);

    void deleteCategory(long categoryId);

    CategoryDto findCategoryByCategoryId(long categoryId);

    List<CategoryDto> findAll();
}
