package com.we.service;

import com.we.model.Enrollment;
import com.we.model.User;

import java.util.List;

public interface EnrollmentService {
    Enrollment enrollUserInCourse(Long userId, Long courseId);

    List<Enrollment> getEnrollmentsByUser(User user);
}
