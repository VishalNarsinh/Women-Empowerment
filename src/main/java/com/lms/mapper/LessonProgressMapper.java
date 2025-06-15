package com.lms.mapper;

import com.lms.dto.LessonProgressDto;
import com.lms.model.LessonProgress;

public class LessonProgressMapper {
    public static LessonProgressDto toDto(LessonProgress lessonProgress) {
        return LessonProgressDto.builder()
                .enrollmentId(lessonProgress.getEnrollment().getEnrollmentId())
                .lessonId(lessonProgress.getLesson().getLessonId())
                .completed(lessonProgress.isCompleted())
                .lastWatchedSeconds(lessonProgress.getLastWatchedSecond())
                .build();
    }
}
