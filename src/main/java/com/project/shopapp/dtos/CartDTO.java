package com.project.shopapp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartDTO {
    private LocalDateTime createDate;
    private String name;
    private Integer quantity;
    private Double price;
    private Long courseId;
    private Long userId;
    private Long paymentId;
}
