package com.we.service;

import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.model.SubCategory;

import java.util.List;

public interface SubCategoryService {
    SubCategoryDto subCategoryToDto(SubCategory subCategory);

    SubCategory dtoToSubCategory(SubCategoryDto subCategoryDto);

    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(SubCategoryDto subCategoryDto);
    void deleteSubCategory(SubCategoryDto subCategoryDto);

    List<SubCategoryDto> findSubCategoriesByCategoryId(CourseDto courseDto);
}
