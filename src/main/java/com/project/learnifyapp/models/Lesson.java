package com.project.learnifyapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Setter
@Getter
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 350)
    private String title;

    @Column(name = "time")
    private String time;

    @Column(name = "video_url", nullable = false, length = 350)
    private String videoUrl;

    @Column(name = "question_and_answer", length = 350)
    private String questionAndAnswer;

    @Column(name = "overview")
    private String overview;

    @Column(name = "note")
    private String note;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
