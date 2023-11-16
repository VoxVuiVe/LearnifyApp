package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Builder
public class RatingDTO {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("number_rating")
    private Integer numberRating;

    @JsonProperty("description")
    private String description;

}
