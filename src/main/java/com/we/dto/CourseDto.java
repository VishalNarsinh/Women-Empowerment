package com.we.dto;

import com.we.model.Image;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourseDto {
    private Long courseId;
    private String courseName;
    private String courseDescription;
    private Image image;
    private Long subCategoryId;
    private List<LessonDto> lessons = new ArrayList<>();

    public List<LessonDto> getLessons() {
        return lessons;
    }
}
