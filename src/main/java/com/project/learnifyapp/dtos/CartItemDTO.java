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
    @JsonProperty("quantity")
    private Integer quantity;

    @NotNull
    @JsonProperty("total_price")
    private Float totalPrice;

    @NotNull
    @JsonProperty("unit_price")
    private Float unitPrice;

    @JsonProperty("cart_id")
    private Long cartId;

    @JsonProperty("course_id")
    private Long courseId;
}
