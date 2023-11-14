package com.project.learnifyapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "percentage")
    private Float percentage;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "start_end")
    private Date startEnd;

    @Column(name = "is_active")
    private boolean active;
}
