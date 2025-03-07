package com.we.service.impl;

import com.we.dto.CategoryDto;
import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.model.SubCategory;
import com.we.repository.SubCategoryRepository;
import com.we.service.CategoryService;
import com.we.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public SubCategoryDto subCategoryToDto(SubCategory subCategory) {
        return modelMapper.map(subCategory, SubCategoryDto.class);
    }

    @Override
    public SubCategory dtoToSubCategory(SubCategoryDto subCategoryDto) {
        return modelMapper.map(subCategoryDto, SubCategory.class);
    }

    @Override
    public SubCategoryDto findSubCategoryBySubCategoryId(long subCategoryId) {
        return subCategoryToDto(subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId)));
    }

    @Override
    public SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto,long categoryId) {
        SubCategory subCategory = dtoToSubCategory(subCategoryDto);
        CategoryDto categoryById = categoryService.findCategoryById(categoryId);
        subCategory.setCategory(categoryService.dtoToCategory(categoryById));
        return subCategoryToDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public SubCategoryDto updateSubCategory(SubCategoryDto subCategoryDto,long subCategoryId,long categoryId) {
        SubCategory subCategoryDB = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId));
        CategoryDto categoryById = categoryService.findCategoryById(categoryId);
        subCategoryDB.setName(subCategoryDto.getName());
        subCategoryDB.setCategory(categoryService.dtoToCategory(categoryById));
        return subCategoryToDto(subCategoryRepository.save(subCategoryDB));
    }

    @Override
    public void deleteSubCategory(long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId));
        subCategoryRepository.delete(subCategory);
    }

    @Override
    public List<SubCategoryDto> findSubCategoriesByCategoryId(CourseDto courseDto) {
        List<SubCategory> byCategoryCategoryId = subCategoryRepository.findByCategoryCategoryId(courseDto.getSubCategoryId());
        return byCategoryCategoryId.stream().map(this::subCategoryToDto).toList();
    }
}
