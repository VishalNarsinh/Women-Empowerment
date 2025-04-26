package com.we.service.impl;

import com.we.exception.ResourceNotFound;
import com.we.model.Course;
import com.we.model.Enrollment;
import com.we.model.User;
import com.we.repository.CourseRepository;
import com.we.repository.EnrollmentRepository;
import com.we.repository.UserRepository;
import com.we.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Enrollment enrollUserInCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFound("Course", "id", courseId));
        Optional<Enrollment> existing = enrollmentRepository.findByUserAndCourse(user, course);
        if(existing.isPresent()){
            return existing.get();
        }
        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .completed(false)
                .build();
        return  enrollmentRepository.save(enrollment);
    }

    @Override
    public List<Enrollment> getEnrollmentsByUser(User user) {

        return List.of();
    }
}
