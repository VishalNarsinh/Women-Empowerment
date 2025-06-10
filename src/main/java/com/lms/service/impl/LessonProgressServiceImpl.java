package com.lms.service.impl;

import com.lms.dto.LessonProgressDto;
import com.lms.exception.ResourceNotFoundException;
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
    public LessonProgress updateLessonProgress(LessonProgressDto lessonProgressDto) {
        Enrollment enrollment = enrollmentRepository.findById(lessonProgressDto.getEnrollmentId()).orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", lessonProgressDto.getEnrollmentId()));
        Lesson lesson = lessonRepository.findById(lessonProgressDto.getLessonId()).orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", lessonProgressDto.getLessonId()));
        LessonProgress lessonProgress = lessonProgressRepository.findByEnrollmentAndLesson(enrollment, lesson).orElse(
                LessonProgress.builder()
                        .enrollment(enrollment)
                        .lesson(lesson)
                        .build()
        );
        lessonProgress.setCompleted(lessonProgressDto.isCompleted());
        lessonProgress.setProgressPercentage(lessonProgressDto.getProgressPercentage());
        lessonProgress.setLastWatchedSecond(lessonProgressDto.getLastWatchedSeconds());
        return lessonProgressRepository.save(lessonProgress);
    }
}
