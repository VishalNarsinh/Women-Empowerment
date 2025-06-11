package com.lms.service;

import com.lms.dto.SubCategoryDto;

import java.util.List;

public interface SubCategoryService {

    SubCategoryDto findSubCategoryBySubCategoryId(long subCategoryId);

    SubCategoryDto saveSubCategory(SubCategoryDto subCategoryDto);
    SubCategoryDto updateSubCategory(SubCategoryDto subCategoryDto,long subCategoryId);
    void deleteSubCategory(long subCategoryId);

    List<SubCategoryDto> findSubCategoriesByCategoryId(long categoryId);

    List<SubCategoryDto> findAll();
}
