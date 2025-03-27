package com.we.service;

import com.we.dto.LessonDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface LessonService {


    LessonDto saveLesson(LessonDto lessonDto, MultipartFile imageFile, MultipartFile videofile) throws IOException;

    LessonDto updateLesson(LessonDto lessonDto,long lessonId,MultipartFile file);

    void deleteLesson(long lessonId);

    LessonDto findLessonById(long lessonId);
    List<LessonDto> findLessonsByCourseId(long courseId);
}
