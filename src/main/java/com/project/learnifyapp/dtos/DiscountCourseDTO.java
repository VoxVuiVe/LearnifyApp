package com.project.learnifyapp.dtos;

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
    private Long courseId;

    @JoinColumn(name = "discount_id")
    private Long discountId;
}
