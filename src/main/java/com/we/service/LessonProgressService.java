package com.we.service;

import com.we.dto.LessonProgressRequest;
import com.we.model.LessonProgress;

public interface LessonProgressService {
    LessonProgress updateLessonProgress(LessonProgressRequest lessonProgressRequest);
}
