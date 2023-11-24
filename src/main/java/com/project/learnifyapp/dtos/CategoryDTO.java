package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.learnifyapp.models.Course;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@ToString
public class CategoryDTO implements Serializable {

    private Long id;

    @NotNull(message = "Category name cannot be null")
    @JsonProperty("name")
    private String name;

    @JsonProperty("parent_id")
    private Long parentId;

    private List<CategoryDTO> children;

    private List<CourseDTO> courses;
}
