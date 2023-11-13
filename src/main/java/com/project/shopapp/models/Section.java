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

<<<<<<< HEAD
     @Column(name = "lesson_id")
     private Long lessonId;

     @Column(name = "title")
     private String title;
=======
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
>>>>>>> 785bea931f34a513a7e3a6429dbddbcaac665a6e

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
