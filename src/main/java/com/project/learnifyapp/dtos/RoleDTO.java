package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO {

    private Long id;
    @NotBlank
    @JsonProperty("name")
    private String name;

    private List<User> user;
}
