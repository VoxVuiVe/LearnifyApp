package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.CourseCart;
import com.project.learnifyapp.models.User;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {

    private Long id;

    @NotNull
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @JsonProperty("total_money")
    private Float totalMoney;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    private List<CourseCart> courseCarts;
}
