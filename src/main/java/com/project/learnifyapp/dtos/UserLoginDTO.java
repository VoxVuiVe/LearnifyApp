package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginDTO {
    @JsonProperty("email")
    @NotBlank(message = "Email must be is required!")
    private String email;

    @NotBlank(message = "Password can't be empty")
    private String password;
}
