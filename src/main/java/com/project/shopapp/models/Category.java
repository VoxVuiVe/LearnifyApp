package com.project.shopapp.models;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    //chưa có course nên chưa thể viết quan hệ với CourseId
}
