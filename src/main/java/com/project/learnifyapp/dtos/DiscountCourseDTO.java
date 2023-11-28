package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("is_delete")
    private Boolean isDelete;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("discount_id")
    private Long discountId;

}