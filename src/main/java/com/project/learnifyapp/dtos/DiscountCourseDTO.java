package com.project.learnifyapp.dtos;

import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Discount;
import jakarta.persistence.JoinColumn;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class DiscountCourseDTO {
    private Long id;

    @JoinColumn(name = "course_id")
    private Course course;

    @JoinColumn(name = "discount_id")
    private Discount discount;

}