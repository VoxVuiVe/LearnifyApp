package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

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
    private Long userId;

    private String cartData;

    private String status;

    private List<CourseDTO> courses;
}
