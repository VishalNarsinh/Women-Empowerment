package com.we.dto;

import lombok.Data;

@Data
public class LessonProgressRequest {
    private long enrollmentId;
    private long lessonId;
    private boolean completed;
    private int progressPercentage;
    private int lastWatchedSeconds;
}
