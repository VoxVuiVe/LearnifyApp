package com.project.learnifyapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="payment_date")
    private Date paymentDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money")
    private Float totalMoney;


    @Column(name = "payment_method")
    private String paymentMethod;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
