package com.lms.service;

import com.lms.model.Enrollment;
import com.lms.model.User;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollUserInCourse(Long userId, Long courseId);

    List<Enrollment> getEnrollmentsByUser(User user);
}
