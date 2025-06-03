package com.lms.service.impl;

import com.lms.dto.LessonProgressRequest;
import com.lms.exception.ResourceNotFound;
import com.lms.model.Enrollment;
import com.lms.model.Lesson;
import com.lms.model.LessonProgress;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.LessonProgressRepository;
import com.lms.repository.LessonRepository;
import com.lms.service.LessonProgressService;
import org.springframework.stereotype.Service;

@Service
public class LessonProgressServiceImpl implements LessonProgressService {
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;

    public LessonProgressServiceImpl(EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository, LessonProgressRepository lessonProgressRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
        this.lessonProgressRepository = lessonProgressRepository;
    }

    @Override
    public LessonProgress updateLessonProgress(LessonProgressRequest lessonProgressRequest) {
        Enrollment enrollment = enrollmentRepository.findById(lessonProgressRequest.getEnrollmentId()).orElseThrow(() -> new ResourceNotFound("Enrollment", "id", lessonProgressRequest.getEnrollmentId()));
        Lesson lesson = lessonRepository.findById(lessonProgressRequest.getLessonId()).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonProgressRequest.getLessonId()));
        LessonProgress lessonProgress = lessonProgressRepository.findByEnrollmentAndLesson(enrollment, lesson).orElse(
                LessonProgress.builder()
                        .enrollment(enrollment)
                        .lesson(lesson)
                        .build()
        );
        lessonProgress.setCompleted(lessonProgressRequest.isCompleted());
        lessonProgress.setProgressPercentage(lessonProgressRequest.getProgressPercentage());
        lessonProgress.setLastWatchedSecond(lessonProgressRequest.getLastWatchedSeconds());
        return lessonProgressRepository.save(lessonProgress);
    }
}
