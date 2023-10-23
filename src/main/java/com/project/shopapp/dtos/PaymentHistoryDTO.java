package com.project.shopapp.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentHistoryDTO {
    private Double totalMoney;
    private Integer numberOfCourse;
    private String transactionStatus;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private Long courseId;
    private Long userId;
}
