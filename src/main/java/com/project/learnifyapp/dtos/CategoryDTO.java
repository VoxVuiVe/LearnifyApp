package com.project.learnifyapp.dtos;

import com.project.learnifyapp.models.Course;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull(message = "Category name cannot be null")
    private String categoryName;

}
