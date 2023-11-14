package com.project.learnifyapp.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name",nullable = false)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
