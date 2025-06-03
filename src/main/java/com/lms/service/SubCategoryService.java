package com.lms.service;

import com.lms.dto.SubCategoryDto;
import com.lms.model.SubCategory;

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
