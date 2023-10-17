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
     private Long sectionId;

     private String title;

     private int quantityLesson;

     private int totalMinutesSection;

}
