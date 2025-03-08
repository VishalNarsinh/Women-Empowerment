package com.we.dto;

import lombok.*;

import java.util.List;

@Data
public class CourseDto {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String imageUrl;
    private Long subCategoryId;
    private List<LessonDto> lessons;
}
