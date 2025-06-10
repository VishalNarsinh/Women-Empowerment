package com.lms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LessonProgressDto {
    private long enrollmentId;
    private long lessonId;
    private boolean completed;
    private int progressPercentage;
    private int lastWatchedSeconds;
}
