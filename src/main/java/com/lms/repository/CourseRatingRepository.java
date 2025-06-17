package com.lms.repository;

import com.lms.model.CourseRating;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CourseRatingRepository extends CrudRepository<CourseRating, Integer> {
    Optional<CourseRating> findByEnrollment_EnrollmentId(Long enrollmentId);

    Optional<CourseRating> findByCourse_CourseId(Long courseId);
}
