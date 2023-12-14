package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.Lesson;
import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class SectionDTO {
    private Long id;

    private String title;

    @JsonProperty("quantity_lesson")
    private Integer quantityLesson;

    @JsonProperty("total_minutes")
    private Integer totalMinutes;

    @JsonProperty("resource")
    private String resource;

    @JsonProperty("is_delete")
    private Boolean isDelete;

    @JsonProperty("course_id")
    private Long courseId;

}
