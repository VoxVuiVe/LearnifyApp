package com.project.shopapp.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discount_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter

public class DiscountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_user_id")
    private Long discountUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

}
