package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavouriteDTO {

    @JsonProperty("favouriteId")
    private Long favouriteId;

    @NotNull(message = "isActive cannot be null")
    @JsonProperty("isActive")
    private Boolean isActive;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("userId")
    private Long userId;

    @NotNull(message = "Course ID cannot be null")
    @JsonProperty("courseId")
    private Long courseId;
}