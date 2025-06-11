package com.lms.service.impl;

import com.lms.dto.EnrollmentResponse;
import com.lms.exception.ResourceNotFoundException;
import com.lms.mapper.EnrollmentMapper;
import com.lms.model.Course;
import com.lms.model.Enrollment;
import com.lms.model.LessonProgress;
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

//    @Override
//    public Enrollment enrollUserInCourse(Long userId, Long courseId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
//        Optional<Enrollment> existing = enrollmentRepository.findByUserAndCourse(user, course);
//        if(existing.isPresent()){
//            return existing.get();
//        }
//        Enrollment enrollment = Enrollment.builder()
//                .user(user)
//                .course(course)
//                .completed(false)
//                .build();
//        return  enrollmentRepository.save(enrollment);
//    }

    @Override
    public Enrollment enrollUserByEmail(String email, Long courseId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        Optional<Enrollment> existing = enrollmentRepository.findByUserAndCourse(user, course);
        if(existing.isPresent()){
            return existing.get();
        }

        Enrollment enrollment = Enrollment.builder()
                .user(user)
                .course(course)
                .completed(false)
                .build();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getMyEnrollment(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        return enrollments.stream().map(EnrollmentMapper::toResponse).toList();
    }


    @Override
    public List<EnrollmentResponse> getEnrollmentsByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        return enrollments.stream().map(EnrollmentMapper::toResponse).toList();
    }

    @Override
    public boolean markEnrollmentAsCompleted(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "enrollmentId", enrollmentId));

        boolean allLessonsCompleted = enrollment.getLessonProgressList().stream()
                .allMatch(LessonProgress::isCompleted);

        if (!allLessonsCompleted) return false;

        enrollment.setCompleted(true);
        enrollmentRepository.save(enrollment);
        return true;
    }

}
