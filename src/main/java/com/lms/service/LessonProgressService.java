package com.lms.service;

import com.lms.dto.LessonProgressRequest;
import com.lms.model.LessonProgress;

public interface LessonProgressService {
    LessonProgress updateLessonProgress(LessonProgressRequest lessonProgressRequest);
}
