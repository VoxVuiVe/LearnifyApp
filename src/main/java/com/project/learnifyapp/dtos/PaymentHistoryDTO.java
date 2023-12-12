package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentHistoryDTO {

    private Long id;

    @Min(value=0, message = "total_money must be >= 0")
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("status")
    private String status;

    @Min(value = 1, message = "Number of course must be >= 1")
    @JsonProperty("number_of_course")
    private Integer numberOfCourse;

    @JsonProperty("transaction_Date")
    private String transactionDate;

    @JsonProperty("payment_id")
    @Min(value = 1, message = "Payment's ID must be > 0")
    private Long paymentId;


}
