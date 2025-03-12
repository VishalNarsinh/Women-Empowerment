package com.we.service.impl;

import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.model.Course;
import com.we.model.SubCategory;
import com.we.repository.CourseRepository;
import com.we.service.CourseService;
import com.we.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final ModelMapper modelMapper;
    private final SubCategoryService subCategoryService;
    private final CourseRepository courseRepository;

    @Override
    public CourseDto courseToDto(Course course) {
        return modelMapper.map(course, CourseDto.class);
    }

    @Override
    public Course dtoToCourse(CourseDto courseDto) {
        return modelMapper.map(courseDto, Course.class);
    }

    @Override
    public CourseDto saveCourse(CourseDto courseDto, MultipartFile file) {
        SubCategory subCategory = subCategoryService.dtoToSubCategory(subCategoryService.findSubCategoryBySubCategoryId(courseDto.getSubCategoryId()));
        Course course = dtoToCourse(courseDto);
        course.setSubCategory(subCategory);
//        course.setImageUrl(file.getOriginalFilename());
        return courseToDto(courseRepository.save(course));
    }

    @Override
    public void deleteCourse(long courseId) {
        courseRepository.delete(courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFound("Course","id",courseId)));
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto,long courseId,MultipartFile file) {

        return null;
    }

    @Override
    public List<CourseDto> findCourseBySubCategoryId(SubCategoryDto subCategoryDto) {
        return List.of();
    }

    @Override
    public CourseDto findCourseByCourseId(long courseId) {
        return courseToDto(courseRepository.findById(courseId).orElseThrow(()->new ResourceNotFound("Course","id",courseId)));
    }

    @Override
    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream().map(this::courseToDto).toList();
    }
}
