package com.we.mapper;

import com.we.dto.LessonDto;
import com.we.model.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public Lesson toEntity(LessonDto dto) {
        if (dto == null) return null;

        Lesson lesson = new Lesson();
        lesson.setLessonId(dto.getLessonId());
        lesson.setLessonName(dto.getLessonName());
        lesson.setLessonContent(dto.getLessonContent());
        lesson.setImage(dto.getImage()); // Direct mapping
        lesson.setVideo(dto.getVideo()); // Direct mapping
        // course will be set externally in service layer
        lesson.setComments(dto.getComments()); // direct mapping

        return lesson;
    }

    public LessonDto toDto(Lesson lesson) {
        if (lesson == null) return null;

        LessonDto dto = new LessonDto();
        dto.setLessonId(lesson.getLessonId());
        dto.setLessonName(lesson.getLessonName());
        dto.setLessonContent(lesson.getLessonContent());
        dto.setImage(lesson.getImage());
        dto.setVideo(lesson.getVideo());
        dto.setCourseId(lesson.getCourse() != null ? lesson.getCourse().getCourseId() : null);
        dto.setComments(lesson.getComments());

        return dto;
    }
}
