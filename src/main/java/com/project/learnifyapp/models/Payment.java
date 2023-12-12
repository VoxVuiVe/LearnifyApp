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

    @Column(name = "total_money")
    private Float totalMoney;

    private String transactionStatus;

    private String bankCode;

    private String transactionNo;

    private String vnp_TxnRef;

    private String vnPayUrl;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "course_id")
//    private Course course;