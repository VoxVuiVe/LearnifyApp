package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("courseId")
    private Long courseId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("createDate")
    private Date createDate;

    @JsonProperty("numberOfLikeComments")
    private int numberOfLikeComments;
}

