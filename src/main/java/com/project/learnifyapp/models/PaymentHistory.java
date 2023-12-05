package com.project.learnifyapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "total_money", nullable = false)
    private Float totalMoney;

    @Column(name = "transaction_date")
    private String transactionDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
