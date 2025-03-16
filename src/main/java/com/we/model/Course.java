package com.we.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long courseId;

    @Column(length = 80, nullable = false)
    private String courseName;

    @Column(length = 500, nullable = false)
    private String courseDescription;


    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;


    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", image=" + image +
                ", lessons=" + lessons +
                '}';
    }
}
