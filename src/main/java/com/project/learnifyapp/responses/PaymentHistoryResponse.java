package com.project.learnifyapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.PaymentHistory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistoryResponse {
    private Long id;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("number_of_course")
    private Integer numberOfCourse;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("course_id")
    private Long courseId;

    public static PaymentHistoryResponse fromPaymentHistory(PaymentHistory paymentHistory) {
        return PaymentHistoryResponse.builder()
                .id(paymentHistory.getId())
                .paymentId(paymentHistory.getPayment().getId())
                .courseId(paymentHistory.getCourse().getId())
                .totalMoney(paymentHistory.getTotalMoney())
                .numberOfCourse(paymentHistory.getNumberOfCourse())
                .transactionId(paymentHistory.getTransactionId())
                .build();
    }
}
