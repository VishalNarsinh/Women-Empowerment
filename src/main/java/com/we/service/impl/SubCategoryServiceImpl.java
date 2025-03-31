package com.we.service.impl;

import com.we.dto.CategoryDto;
import com.we.dto.SubCategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.mapper.CategoryMapper;
import com.we.model.SubCategory;
import com.we.repository.CategoryRepository;
import com.we.repository.SubCategoryRepository;
import com.we.service.CategoryService;
import com.we.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private static final Logger log = LoggerFactory.getLogger(SubCategoryServiceImpl.class);
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final SubCategoryRepository subCategoryRepository;
//    private final CourseService courseService;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

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
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId));
        return categoryMapper.toDto(subCategory);
//        return SubCategoryDto.builder()
//                .name(subCategory.getName())
//                .categoryId(subCategory.getCategory().getCategoryId())
//                .courses(subCategory.getCourses().stream().map(courseService::courseToDto).toList())
//                .build();

//        return subCategoryToDto(subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId)));
    }

    @Override
    public SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto) {
        SubCategory subCategory = categoryMapper.toEntity(subCategoryDto);
        log.info("SubCategory {}", subCategory);
        subCategory.setCategory(categoryRepository.findById(subCategoryDto.getCategoryId()).orElseThrow(() -> new ResourceNotFound("Category", "categoryId", subCategoryDto.getCategoryId())));
        return categoryMapper.toDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public SubCategoryDto updateSubCategory(SubCategoryDto subCategoryDto,long subCategoryId) {
        SubCategory subCategoryDB = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId));
        CategoryDto categoryById = categoryService.findCategoryByCategoryId(subCategoryDto.getCategoryId());
        subCategoryDB.setName(subCategoryDto.getName());
        subCategoryDB.setCategory(categoryMapper.toEntity(categoryById));
        return categoryMapper.toDto(subCategoryRepository.save(subCategoryDB));
    }

    @Override
    public void deleteSubCategory(long subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFound("SubCategory", "subCategoryId", subCategoryId));
        subCategoryRepository.delete(subCategory);
    }

    @Override
    public List<SubCategoryDto> findSubCategoriesByCategoryId(long categoryId) {
        List<SubCategory> list = subCategoryRepository.findByCategoryCategoryId(categoryId);
        return list.stream().map(categoryMapper::toDto).toList();
    }
}
