package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "course_id")
    private Long courseId;

    @Column (name = "title", columnDefinition = "nvarchar(255)")
    private String title;

    @Column (name = "price")
    private double price;

    @Column (name = "teacher", columnDefinition = "nvarchar(50)")
    private String teacher;

    @Column (name = "start_time")
    private LocalDateTime start_time;

    @Column (name = "end_time")
    private LocalDateTime end_time;

    @Column (name = "enrollment_count")
    private double enrollment_count;

    @Column (name = "last_update_time")
    private LocalDateTime last_update_time;

    @Column (name = "image", columnDefinition = "nvarchar(255)")
    private String image;
}
