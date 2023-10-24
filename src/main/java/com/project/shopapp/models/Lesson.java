package com.project.shopapp.models;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "lesson")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "leeson_id")
    private Section section;

    @Column(name = "title",length = 350)
    private String title;

    @Column(name = "time")
    private String time;

    @Column(name = "video_url",nullable = false , length = 350)
    private String videoUrl;

    @Column(name = "question_and_answer",length = 350)
    private String questionAndAnswer;

    @Column(name = "overview")
    private String overview;

    @Column(name = "note")
    private String note;
}
