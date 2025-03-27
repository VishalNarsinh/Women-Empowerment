package com.we.repository;

import com.we.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson,Long> {
    @Modifying
    @Query("delete from Lesson l where l.lessonId = :lessonId")
    void deleteByLessonId(long lessonId);

}
