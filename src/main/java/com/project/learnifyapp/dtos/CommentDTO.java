package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    @JsonProperty("create_date")
    private Date createDate;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("quantity_like")
    private Integer quantity;

    @JsonProperty("is_like")
    private Boolean isLike;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("lesson_id")
    private Long lessonId;


}

