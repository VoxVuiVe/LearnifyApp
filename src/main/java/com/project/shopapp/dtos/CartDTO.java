package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartDTO {

    @NotNull
    @JsonProperty("create_date")
    private LocalDateTime createDate;

    @NotNull
    @JsonProperty("name")
    private String name;

    @NotNull
    @Min(0)
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @DecimalMin("0.0")
    @JsonProperty("price")
    private Double price;

    @NotNull
    @JsonProperty("course_id")
    private Long courseId;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    @NotNull
    @JsonProperty("payment_id")
    private Long paymentId;
}
