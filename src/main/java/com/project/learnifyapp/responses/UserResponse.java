package com.project.learnifyapp.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends BaseResponse{
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String password;
    private LocalDate dateOfBirth;
    private int facebookAccountId;
    private int googleAccountId;

    @JsonProperty("role_id")
    private Long roleId;

    @JsonIgnore
    @JsonProperty("cart_id")
    private Long cartId;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .address(user.getAddress())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .roleId(user.getRole().getId())
                .build();
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
