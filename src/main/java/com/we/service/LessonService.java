package com.we.service;

import com.we.dto.LessonDto;
import com.we.model.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonService {

    LessonDto lessonToDto(Lesson lesson);

    Lesson dtoToLesson(LessonDto lessonDto);

    LessonDto saveLesson(LessonDto lessonDto, MultipartFile file);

    LessonDto updateLesson(LessonDto lessonDto,long lessonId,MultipartFile file);

    void deleteLesson(long lessonId);

    LessonDto findLessonById(long lessonId);
    List<LessonDto> findLessonsByCourseId(long courseId);
}
