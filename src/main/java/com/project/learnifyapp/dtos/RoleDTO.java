package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.models.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO extends Role implements Serializable {

    private Long id;
    @NotBlank
    @JsonProperty("name")
    private String name;

    @JsonProperty("role")
    private List<User> user;
}
