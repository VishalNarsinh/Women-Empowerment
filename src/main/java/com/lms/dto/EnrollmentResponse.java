package com.lms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentResponse {
    private long enrollmentId;
    private long userId;
    private long courseId;
    private boolean completed;
}
