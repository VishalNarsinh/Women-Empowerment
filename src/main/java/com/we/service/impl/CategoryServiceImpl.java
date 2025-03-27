package com.we.service.impl;

import com.we.dto.CategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.mapper.CategoryMapper;
import com.we.model.Category;
import com.we.repository.CategoryRepository;
import com.we.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

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
        Category category = categoryMapper.toEntity(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto,long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryDto.getCategoryId()));
        category.setName(categoryDto.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id", categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto findCategoryByCategoryId(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "id",categoryId));
        return categoryMapper.toDto(category);}

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }
}
