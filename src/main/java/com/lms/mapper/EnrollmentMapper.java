package com.lms.mapper;

import com.lms.dto.EnrollmentResponse;
import com.lms.model.Enrollment;

public class EnrollmentMapper {
    public static EnrollmentResponse toResponse(Enrollment enrollment) {
        return EnrollmentResponse
                .builder()
                .userId(enrollment.getUser().getUserId())
                .courseId(enrollment.getCourse().getCourseId())
                .enrollmentId(enrollment.getEnrollmentId())
                .completed(enrollment.isCompleted())
                .build();
    }
}
