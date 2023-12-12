package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseDTO implements Serializable {

    private Long id;

    @NotNull
    @JsonProperty("title")
    private String title;

    @NotNull
    @JsonProperty("price")
    private Float price;

    @JsonProperty("start_time")
    private LocalDateTime startTime = LocalDateTime.now();

    @JsonProperty("end_time")
    private LocalDateTime endTime = LocalDateTime.now();

    @JsonProperty("enrollment_count")
    private Integer enrollmentCount = 0;

    @NotNull
    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("is_delete")
    private Boolean isDelete = false;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("user_id")
    private Long userId;

    @NotNull
    @JsonProperty("description")
    private String description;
}
