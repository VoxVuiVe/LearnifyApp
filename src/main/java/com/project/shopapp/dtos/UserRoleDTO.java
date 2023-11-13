package com.project.shopapp.dtos;
 
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRoleDTO {

    @JsonProperty("userRoleId")
    private Long userRoleId;

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("courseId")
    private Long courseId;
}

