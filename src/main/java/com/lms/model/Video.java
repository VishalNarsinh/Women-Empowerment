package com.lms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private long videoId;
    private String videoUrl;
    private String videoName;
    private String contentType;
    @Column(nullable = false)
    private String processingStatus = "PENDING";
}
