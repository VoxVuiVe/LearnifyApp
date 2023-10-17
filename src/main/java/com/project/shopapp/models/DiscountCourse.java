package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
@Table(name = "discount_Course")
public class DiscountCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "discount_course_id")
    private Long discountCourseId;

    @ManyToOne
    @JoinColumn (name = "course_id")
    Course course_id;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    Discount discount_id;
}
