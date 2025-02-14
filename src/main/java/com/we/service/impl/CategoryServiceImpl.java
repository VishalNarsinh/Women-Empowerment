package com.we.service.impl;

import com.we.dto.CategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.model.Category;
import com.we.repository.CategoryRepository;
import com.we.repository.CourseRepository;
import com.we.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category dtoToCategory(CategoryDto categoryDto) {
        return modelMapper.map( categoryDto, Category.class);
    }

    @Override
    public CategoryDto categoryToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        return categoryToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getCategoryId()).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryDto.getCategoryId()));
        category.setName(categoryDto.getName());
        return categoryToDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {

    }
}
