package com.project.learnifyapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
@Table(name = "sections")
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Lob
    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "quantity_lesson")
    private Integer quantityLesson;

    @Column(name = "total_minutes")
    private Integer totalMinutes;

    @Column(name = "resource")
    private String resource;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
