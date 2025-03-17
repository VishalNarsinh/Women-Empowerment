package com.we.dto;

import com.we.model.Comment;
import com.we.model.Image;
import com.we.model.Video;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long lessonId;
    private String lessonName;
    private String lessonContent;
    private Image image;
    private Video video;
    private Long courseId;
    private List<Comment> comments=new ArrayList<>();
}
