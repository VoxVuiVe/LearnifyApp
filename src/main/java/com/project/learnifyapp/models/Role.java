package com.project.learnifyapp.models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<User> user;

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
    public static String TEACHER = "TEACHER";
}

