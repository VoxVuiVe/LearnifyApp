package com.project.learnifyapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImage {
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "image_url", length = 300)
    @JsonProperty("image_url")
    private String imageUrl;
}
