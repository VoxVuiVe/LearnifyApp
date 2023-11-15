package com.project.learnifyapp.models;

import lombok.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Table(name = "sections")
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "quantity_lesson")
    private Integer quantityLesson;

    @Column(name = "total_minutes")
    private Integer totalMinutes;

    @Column(name = "resource")
    private String resource;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lesson;
}
