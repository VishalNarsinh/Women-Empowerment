package com.we.service;

import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.model.SubCategory;

import java.util.List;

public interface SubCategoryService {
    SubCategoryDto subCategoryToDto(SubCategory subCategory);

    SubCategory dtoToSubCategory(SubCategoryDto subCategoryDto);

    SubCategoryDto findSubCategoryBySubCategoryId(long subCategoryId);

    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(SubCategoryDto subCategoryDto,long subCategoryId);
    void deleteSubCategory(long subCategoryId);

    List<SubCategoryDto> findSubCategoriesByCategoryId(long categoryId);
}
