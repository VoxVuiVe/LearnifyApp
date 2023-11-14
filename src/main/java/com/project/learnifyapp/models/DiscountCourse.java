package com.project.learnifyapp.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// @MappedSuperclass
@Table(name = "discount_course")
public class DiscountCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_course_id")
    private Long discountCourseId;

    @ManyToOne
    @JoinColumn(name = "course_id" , nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "discount_id" , nullable = false)
    private Discount discount;
}
