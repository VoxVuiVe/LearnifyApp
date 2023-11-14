package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDTO {

    @NotBlank
    @JsonProperty("name")
    private String name;
}
