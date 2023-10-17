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
    private Long lessonId;

    private String title;

    private String time;

    private String videoUrl;

    private String questionAndAnswer;

    private String overview;

    private String note;
}
