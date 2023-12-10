package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DiscountDTO {

    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("percentage")
    private Float percentage;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("startEnd")
    private LocalDateTime startEnd;

    @JsonProperty("isActive")
    private Boolean isActive;

}
