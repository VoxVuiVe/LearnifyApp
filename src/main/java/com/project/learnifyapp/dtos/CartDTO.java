package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartDTO {

    private Long id;

    @NotNull
    @JsonProperty("total_item")
    private Integer totalItem;

    @NotNull
    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("user_id")
    private Long userId;

    private Set<CartItemDTO> cartItems;
}
