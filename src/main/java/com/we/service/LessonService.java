package com.we.service;

import com.we.dto.CourseDto;
import com.we.dto.LessonDto;
import com.we.model.Lesson;

import java.util.List;

public interface LessonService {

    LessonDto lessonToDto(Lesson lesson);

    Lesson dtoToLesson(LessonDto lessonDto);

    LessonDto saveLesson(LessonDto lessonDto);

    LessonDto updateLesson(LessonDto lessonDto);

    void deleteLesson(LessonDto lessonDto);

    List<LessonDto> findLessonsByCourseId(CourseDto courseDto);
}
