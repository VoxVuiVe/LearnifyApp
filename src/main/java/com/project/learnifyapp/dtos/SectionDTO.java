package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.Lesson;
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
    @NotNull
    private String title;
    private Integer quantityLesson;
    private Integer totalMinutes;
    private String resource;
    @JsonProperty("course_id")
    private Long courseId;
    private List<Lesson> lesson;
}
