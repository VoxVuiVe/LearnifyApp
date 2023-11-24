package com.project.learnifyapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// @MappedSuperclass
@Table(name = "discount_courses")
public class DiscountCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id" , nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "discount_id" , nullable = false)
    private Discount discount;
}