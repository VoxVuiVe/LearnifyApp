package com.project.learnifyapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "user_courses")
@ToString
public class UserCourse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column (name = "enrollment_date")
    private Date enrollmentDate;

    @Column (name = "payment_status")
    private Boolean paymentStatus;
}
