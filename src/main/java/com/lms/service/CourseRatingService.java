package com.lms.service;

import com.lms.dto.CourseRatingRequest;
import com.lms.model.CourseRating;

public interface CourseRatingService {
    CourseRating saveOrUpdateRating(String userEmail, CourseRatingRequest request);
}
