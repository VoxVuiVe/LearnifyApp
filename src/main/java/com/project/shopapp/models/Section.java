package com.project.shopapp.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long sectionId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "quantity_lesson")
    private int quantityLesson;

    @Column(name = "total_minutes_section")
    private int totalMinutesSection;

    @Column(name = "resource")
    private String resource;
}
