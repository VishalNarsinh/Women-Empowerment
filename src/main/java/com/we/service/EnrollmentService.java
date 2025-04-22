package com.we.service;

import com.we.model.Enrollment;

public interface EnrollmentService {
    Enrollment enrollUserInCourse(Long userId, Long courseId);
}
