package com.we.service;

import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.model.Course;

import java.util.List;

public interface CourseService {

    CourseDto courseToDto(Course course);

    Course dtoToCourse(CourseDto courseDto);

    CourseDto saveCourse(CourseDto courseDto);

    void deleteCourse(CourseDto courseDto);

    CourseDto updateCourse(CourseDto courseDto);

    List<CourseDto> findCourseBySubCategoryId(SubCategoryDto subCategoryDto);

}
