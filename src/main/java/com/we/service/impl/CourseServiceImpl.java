package com.we.service.impl;

import com.we.dto.CourseDto;
import com.we.dto.SubCategoryDto;
import com.we.exception.ResourceNotFound;
import com.we.model.Course;
import com.we.model.Image;
import com.we.model.SubCategory;
import com.we.repository.CourseRepository;
import com.we.repository.SubCategoryRepository;
import com.we.service.CourseService;
import com.we.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
    private final ModelMapper modelMapper;
//    private final SubCategoryService subCategoryService;
    private final CourseRepository courseRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ImageService imageService;


    @Override
    public CourseDto courseToDto(Course course) {
        return CourseDto.builder()
                .courseName(course.getCourseName())
                .courseDescription(course.getCourseDescription())
                .image(course.getImage())
//                .lessons(course.getLessons().stream().map(lesson->modelMapper.map(lesson, LessonDto.class)).toList())
                .courseId(course.getCourseId())
                .subCategoryId(course.getSubCategory().getSubCategoryId())
                .build();

    }

    @Override
    public Course dtoToCourse(CourseDto courseDto) {
        Course.builder()
                .courseName(courseDto.getCourseName())
                .courseDescription(courseDto.getCourseDescription())
                .image(courseDto.getImage())
//                .lessons(courseDto.getLessons().stream().map(lesson->modelMapper.map(lesson, Lesson.class)).toList())
                .build();
        return modelMapper.map(courseDto, Course.class);
    }

    @Override
    public CourseDto saveCourse(CourseDto courseDto, MultipartFile file) throws IOException {
        SubCategory subCategory = subCategoryRepository.findById(courseDto.getSubCategoryId()).orElseThrow(() -> new ResourceNotFound("SubCategory", "id", courseDto.getSubCategoryId()));
        Course course = dtoToCourse(courseDto);
        course.setSubCategory(subCategory);
        Image image = imageService.uploadImage(file, "course");
        log.info("image {}", image);
        course.setImage(image);
        return courseToDto(courseRepository.save(course));
    }

    @Override
    @Transactional
    public void deleteCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFound("Course", "id", courseId));
        log.info("deleting course {}", course);
        imageService.deleteImage(course.getImage().getImageId());
        course.setImage(null);
//        courseRepository.save(course);
//        courseRepository.delete(course);
        courseRepository.deleteCourseById(courseId);
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
