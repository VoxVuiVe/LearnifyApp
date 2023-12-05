package com.project.learnifyapp.dtos;
import jakarta.persistence.Column;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private Long id;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_date")
    private Date paymentDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("course_id")
    private Long courseId;

    private String vnPayUrl;
}
