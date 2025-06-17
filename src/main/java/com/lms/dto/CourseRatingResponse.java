package com.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRatingResponse {
    private int rating;
    private String comment;
    private String courseName;
    private String userName;
    private LocalDateTime ratedAt;
}

