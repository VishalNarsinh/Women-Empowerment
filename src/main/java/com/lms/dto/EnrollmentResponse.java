package com.lms.dto;

import lombok.Data;

@Data
public class EnrollmentResponse {
    private long enrollmentId;
    private long userId;
    private long courseId;
    private boolean completed;
}
