package com.project.learnifyapp.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.Role;
import com.project.learnifyapp.models.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends BaseResponse{

    private Long id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("password")
    private String password;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("is_active")
    private boolean active;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role_id")
    private Role roleId;

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
//                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .facebookAccountId(user.getFacebookAccountId())
                .googleAccountId(user.getGoogleAccountId())
                .roleId(user.getRole())
                .build();
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        return userResponse;
    }
}
