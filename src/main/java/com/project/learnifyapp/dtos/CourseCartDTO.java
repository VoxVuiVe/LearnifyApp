package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseCartDTO implements Serializable {

    private Long id;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("cart_id")
    private Long cartId;
}
