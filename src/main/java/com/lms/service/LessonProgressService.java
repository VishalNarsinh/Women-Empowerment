package com.lms.service;

import com.lms.dto.LessonProgressDto;
import com.lms.model.LessonProgress;

public interface LessonProgressService {
    LessonProgress updateLessonProgress(LessonProgressDto lessonProgressDto);
}
