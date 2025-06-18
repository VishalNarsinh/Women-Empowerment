package com.lms.service.impl;

import com.lms.dto.CourseRatingRequest;
import com.lms.exception.ResourceNotFoundException;
import com.lms.model.CourseRating;
import com.lms.model.Enrollment;
import com.lms.repository.CourseRatingRepository;
import com.lms.repository.EnrollmentRepository;
import com.lms.service.CourseRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseRatingServiceImpl implements CourseRatingService {
    private final CourseRatingRepository courseRatingRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public CourseRating saveOrUpdateRating(String userEmail, CourseRatingRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(request.getEnrollmentId()).orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", request.getEnrollmentId()));
        if(!enrollment.getUser().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("You are not authorized to rate this course.");
        }
        if(request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        CourseRating rating = courseRatingRepository.findByEnrollment_EnrollmentId(request.getEnrollmentId())
                .orElse(CourseRating.builder()
                        .enrollment(enrollment)
                        .course(enrollment.getCourse())
                        .build());
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setRatedAt(LocalDateTime.now());
        return courseRatingRepository.save(rating);
    }

    @Override
    public Map<String, ? extends Number> getAverageRating(Long courseId) {
        List<CourseRating> courseRatings = courseRatingRepository.findByCourse_CourseId(courseId);
        if(courseRatings.isEmpty()) {return Map.of("averageRating", 0.0, "totalRating", 0);}
        double average = courseRatings.stream().mapToInt(CourseRating::getRating).average().orElse(0.0);
        double formattedAverage = Math.round(average * 10.0) / 10.0;
        return Map.of("averageRating", formattedAverage, "totalRating", courseRatings.size());
    }
}
