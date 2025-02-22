package com.we.dto;

import com.we.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long lessonId;
    private String lessonName;
    private String lessonContent;
    private String imageUrl;
    private String videoUrl;
    private Long courseId;
    private List<Comment> comments;
}
