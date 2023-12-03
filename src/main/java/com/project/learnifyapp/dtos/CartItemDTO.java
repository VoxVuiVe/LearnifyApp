package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartItemDTO {
    private Long id;

    @NotNull
    @JsonProperty("total_price")
    private Float totalPrice;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    private String cartData;

    @JsonProperty("course_id")
    private String courseId;
}
