package com.project.learnifyapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "percentage", nullable = false)
    private Float percentage;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "start_end")
    private LocalDateTime startEnd;

    @Column(name = "is_active")
    private boolean active;
}
