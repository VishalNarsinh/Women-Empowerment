package com.we.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {
    private long videoId;
    private String videoUrl;
    private String videoName;
    private String contentType;
    private long lessonId;
    private String processingStatus;
}
