package com.project.learnifyapp.dtos.userDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseInfoDTO implements Serializable {
    private Long courseId;
    private String thumbnail;
    private String createAt;
    private String title;
    private String fullname;
    private String imageUrl;
    private Long sectionsId;
    private Integer quantityLesson;
}
