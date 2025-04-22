package com.we.dto;

import lombok.Data;

@Data
public class EnrollmentResponse {
    private long enrollmentId;
    private String userId;
    private String courseId;
    private boolean completed;
}
