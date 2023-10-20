package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "section")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter@Getter
public class Section {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "section_id")
     private Long sectionId;

     @Column(name = "lesson_id")
     private Long lessonId;

     @Column(name = "title")
     private String title;

     @Column(name = "quantity_Lesson")
     private int quantityLesson;

     @Column(name = "total_minutes_section")
     private int totalMinutesSection;

}
