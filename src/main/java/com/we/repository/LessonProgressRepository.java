package com.we.repository;

import com.we.model.Enrollment;
import com.we.model.Lesson;
import com.we.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Optional<LessonProgress> findByEnrollmentAndLesson(Enrollment enrollment, Lesson lesson);
}
