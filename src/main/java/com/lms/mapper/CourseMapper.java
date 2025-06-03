package com.lms.mapper;

import com.lms.dto.CourseDto;
import com.lms.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    private final LessonMapper lessonMapper;

    public CourseMapper(LessonMapper lessonMapper) {
        this.lessonMapper = lessonMapper;
    }

    public Course toEntity(CourseDto dto) {
        if (dto == null) return null;

        Course course = new Course();
        course.setCourseId(dto.getCourseId());
        course.setCourseName(dto.getCourseName());
        course.setCourseDescription(dto.getCourseDescription());
        course.setImage(dto.getImage()); // No mapper needed
        // subCategoryId will be set externally in service layer

//        if (dto.getLessons() != null) {
//            List<Lesson> lessons = dto.getLessons().stream()
//                    .map(lessonDto -> {
//                        Lesson lesson = lessonMapper.toEntity(lessonDto);
//                        lesson.setCourse(course); // back reference
//                        return lesson;
//                    })
//                    .collect(Collectors.toList());
//            course.setLessons(lessons);
//        }

        return course;
    }

    public CourseDto toDto(Course course) {
        if (course == null) return null;

        CourseDto dto = new CourseDto();
        dto.setCourseId(course.getCourseId());
        dto.setCourseName(course.getCourseName());
        dto.setCourseDescription(course.getCourseDescription());
        dto.setImage(course.getImage()); // No mapping needed
        dto.setSubCategoryId(course.getSubCategory() != null ? course.getSubCategory().getSubCategoryId() : null);

//        if (course.getLessons() != null) {
//            List<LessonDto> lessonDtos = course.getLessons().stream()
//                    .map(lessonMapper::toDto)
//                    .collect(Collectors.toList());
//            dto.setLessons(lessonDtos);
//        }

        return dto;
    }
}
