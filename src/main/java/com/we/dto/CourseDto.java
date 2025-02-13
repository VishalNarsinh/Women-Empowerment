package com.we.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private String imageUrl;
    private Long subCategoryId;
    private List<LessonDto> lessons;
}
