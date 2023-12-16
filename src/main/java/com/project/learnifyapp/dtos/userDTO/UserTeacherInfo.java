package com.project.learnifyapp.dtos.userDTO;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserTeacherInfo implements Serializable {
    private Long id;
    private String fullName;
    private String image;
    private Long quantityCourse;
    private String roleName;
}
