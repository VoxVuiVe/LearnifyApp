package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class LessonDTO implements Serializable {

    private Long id;
    @NotNull(message = "title cannot be null")
    @JsonProperty("title")
    private String title;

    @NotNull(message = "time cannot be null")
    @JsonProperty("time")
    private String time;

    @NotNull(message = "videoUrl cannot be null")
    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("question_and_answer")
    private String questionAndAnswer;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("note")
    private String note;

    @JsonProperty("comment")
    private String comment;

    @NotNull
    @JsonProperty("section_id")
    private Long sectionId;

}
