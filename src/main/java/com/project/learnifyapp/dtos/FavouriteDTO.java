package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteDTO implements Serializable {

    private Long id;

    @NotNull(message = "isActive cannot be null")
    private Boolean isActive;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private Long userId;

    @NotNull(message = "Course ID cannot be null")
    @JsonProperty("course_id")
    private Long courseId;
}