package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_course")
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_course_id")
    private Long userCourseId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User userId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    Course courseId;

    @Column (name = "enrollment_date")
    private Date enrollmentDate;

}
