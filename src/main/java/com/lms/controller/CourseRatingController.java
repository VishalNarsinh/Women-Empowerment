package com.lms.controller;

import com.lms.dto.CourseRatingRequest;
import com.lms.dto.CourseRatingResponse;
import com.lms.model.CourseRating;
import com.lms.service.CourseRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/ratings")
public class CourseRatingController {

    private final CourseRatingService courseRatingService;

    public CourseRatingController(CourseRatingService courseRatingService) {
        this.courseRatingService = courseRatingService;
    }

    @PostMapping("/rate")
    public ResponseEntity<?> rateCourse(@RequestBody CourseRatingRequest courseRatingRequest, Principal principal) {
        CourseRating courseRating = courseRatingService.saveOrUpdateRating(principal.getName(), courseRatingRequest);
        CourseRatingResponse  courseRatingResponse = new CourseRatingResponse();
        courseRatingResponse.setRating(courseRating.getRating());
        courseRatingResponse.setComment(courseRating.getComment());
        courseRatingResponse.setCourseName(courseRating.getCourse().getCourseName());
        courseRatingResponse.setUserName(courseRating.getEnrollment().getUser().getFirstName());
        courseRatingResponse.setRatedAt(courseRating.getRatedAt());
        return ResponseEntity.ok(courseRatingResponse);
    }

    @GetMapping("/average-rating/{courseId}")
    public ResponseEntity<?> getAverageRating(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseRatingService.getAverageRating(courseId));
    }
}
