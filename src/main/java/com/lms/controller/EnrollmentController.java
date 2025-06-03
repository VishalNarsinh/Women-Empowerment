package com.lms.controller;

import com.lms.dto.EnrollmentRequest;
import com.lms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<?> enrollUser(@RequestBody EnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.enrollUserInCourse(request.getUserId(), request.getCourseId()));
    }
}
