package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserCourseDTO implements Serializable {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("enrollment_date")
    private LocalDateTime enrollmentDate = LocalDateTime.now();

    @JsonProperty("payment_status")
    private Boolean paymentStatus;;
}
