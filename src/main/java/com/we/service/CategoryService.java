package com.we.service;

import com.we.dto.CategoryDto;
import com.we.model.Category;

import java.util.List;

public interface CategoryService {

    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);

    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,long categoryId);

    void deleteCategory(long categoryId);

    CategoryDto findCategoryByCategoryId(long categoryId);

    List<CategoryDto> findAll();
}
