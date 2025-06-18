package com.lms.service;

import com.lms.dto.CourseRatingRequest;
import com.lms.model.CourseRating;

import java.util.Map;

public interface CourseRatingService {
    CourseRating saveOrUpdateRating(String userEmail, CourseRatingRequest request);
    Map<String, ? extends Number> getAverageRating(Long courseId);

}
