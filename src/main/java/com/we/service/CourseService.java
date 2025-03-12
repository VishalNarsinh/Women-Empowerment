package com.we.service;

import com.we.dto.CategoryDto;
import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.model.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    CourseDto courseToDto(Course course);

    Course dtoToCourse(CourseDto courseDto);

    CourseDto saveCourse(CourseDto courseDto,MultipartFile file);

    void deleteCourse(long courseId);

    CourseDto updateCourse(CourseDto courseDto,long courseId, MultipartFile file);

    List<CourseDto> findCourseBySubCategoryId(SubCategoryDto subCategoryDto);

    CourseDto findCourseByCourseId(long courseId);

    List<CourseDto> findAll();
}
