package com.lms.repository;

import com.lms.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    @Modifying
    @Query("delete from Lesson l where l.lessonId = :lessonId")
    void deleteByLessonId(long lessonId);

    List<Lesson> findByCourseCourseId(Long courseId);

}
