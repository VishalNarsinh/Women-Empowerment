package com.lms.service.impl;

import com.lms.exception.ResourceNotFound;
import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.User;
import com.lms.repository.CourseRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.repository.UserRepository;
import com.lms.service.EnrollmentService;
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
