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

    private Long id;
    @NotNull
    @DecimalMin("0.0")
    @JsonProperty("total_money")
    private Double totalMoney;

    @NotNull
    @Min(0)
    @JsonProperty("number_of_course")
    private Integer numberOfCourse;

    @NotNull
    @JsonProperty("price")
    private Float price;

    @NotBlank
    @JsonProperty("transaction_id")
    private String transactionId;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("payment_id")
    private Long paymentId;
}
