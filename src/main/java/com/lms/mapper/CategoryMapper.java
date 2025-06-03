package com.lms.mapper;

import com.lms.dto.CategoryDto;
import com.lms.dto.SubCategoryDto;
import com.lms.model.Category;
import com.lms.model.SubCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {


    private final CourseMapper courseMapper;

    public Category toEntity(CategoryDto dto) {
        Category category = new Category();
//        category.setCategoryId(dto.getCategoryId());
        category.setName(dto.getName());

//        if (dto.getSubCategories() != null) {
//            List<SubCategory> subCategories = dto.getSubCategories().stream()
//                    .map(this::toEntity)
//                    .collect(Collectors.toList());
//            category.setSubCategories(subCategories);
//        }

        return category;
    }

    public CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());

//        if (category.getSubCategories() != null) {
//            List<SubCategoryDto> subCategoryDtos = category.getSubCategories().stream()
//                    .map(this::toDto)
//                    .collect(Collectors.toList());
//            dto.setSubCategories(subCategoryDtos);
//        }

        return dto;
    }

    public SubCategory toEntity(SubCategoryDto dto) {
        SubCategory entity = new SubCategory();
//        entity.setSubCategoryId(dto.getSubCategoryId());
        entity.setName(dto.getName());
        entity.setCategory(new Category(dto.getCategoryId()));
//        if (dto.getCourses() != null) {
//            List<Course> courses = dto.getCourses().stream()
//                    .map(courseMapper::toEntity)
//                    .collect(Collectors.toList());
//            entity.setCourses(courses);
//        }

        return entity;
    }

    public SubCategoryDto toDto(SubCategory entity) {
        SubCategoryDto dto = new SubCategoryDto();
        dto.setSubCategoryId(entity.getSubCategoryId());
        dto.setName(entity.getName());
        dto.setCategoryId(entity.getCategory().getCategoryId());

//        if (entity.getCourses() != null) {
//            List<CourseDto> courseDtos = entity.getCourses().stream()
//                    .map(courseMapper::toDto)
//                    .collect(Collectors.toList());
//            dto.setCourses(courseDtos);
//        }
        return dto;
    }
}