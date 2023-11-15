package com.project.learnifyapp.models;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payment_history")
public class PaymentHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_money", nullable = false)
    private Double totalMoney;

    @Column(name = "number_of_course", nullable = false)
    private Integer numberOfCourse;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
