package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentHistoryDTO {

    @NotNull
    @DecimalMin("0.0")
    @JsonProperty("total_money")
    private Double totalMoney;

    @NotNull
    @Min(0)
    @JsonProperty("number_of_course")
    private Integer numberOfCourse;

    @NotBlank
    @JsonProperty("transaction_status")
    private String transactionStatus;

    @JsonProperty("payment_date")
    private LocalDateTime paymentDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("course_id")
    private Long courseId;

    @JsonProperty("user_id")
    private Long userId;
}
