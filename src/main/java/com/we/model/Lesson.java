package com.we.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private long lessonId;

    @Column(length = 80, nullable = false)
    private String lessonName;

    private String lessonContent;

    @OneToOne
    @JoinColumn(name = "image_id",nullable = false)
    private Image image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id",nullable = false)
    private Video video;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;


    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
