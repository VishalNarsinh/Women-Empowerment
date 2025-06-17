package com.lms.service.impl;

import com.lms.dto.CourseRatingRequest;
import com.lms.exception.ResourceNotFoundException;
import com.lms.model.CourseRating;
import com.lms.repository.CourseRatingRepository;
import com.lms.service.CourseRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseRatingServiceImpl implements CourseRatingService {
    private final CourseRatingRepository courseRatingRepository;

    @Override
    public CourseRating saveOrUpdateRating(String userEmail, CourseRatingRequest request) {
        courseRatingRepository.findByEnrollment_EnrollmentId(request.getEnrollmentId()).orElseThrow(()->new ResourceNotFoundException("Enrollment","id",request.getEnrollmentId()));
        return null;
    }
}
