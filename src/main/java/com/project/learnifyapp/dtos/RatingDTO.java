package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RatingDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createDate")
    private Date createDate;
}
