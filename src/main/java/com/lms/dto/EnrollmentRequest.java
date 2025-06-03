package com.lms.dto;

import lombok.Data;

@Data
public class EnrollmentRequest {
    private long userId;
    private long courseId;
}
