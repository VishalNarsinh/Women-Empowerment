package com.we.service;

import com.we.dto.CategoryDto;
import com.we.model.Category;

public interface CategoryService {

    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);

    CategoryDto saveCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,long categoryId);

    void deleteCategory(CategoryDto categoryDto);

    CategoryDto findCategoryById(long categoryId);
}
