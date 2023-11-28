package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;

    @JsonProperty("create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @JsonProperty("comment")
    @NotNull
    @NotEmpty
    @Size(max = 250)
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

