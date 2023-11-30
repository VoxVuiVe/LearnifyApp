package com.project.learnifyapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseDTO implements Serializable {

    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("teacher")
    private String teacher;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("enrollment_count")
    private Integer enrollmentCount;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("category_id")
    private Long categoryId;

    private List<CartItemDTO> cartItemDTOs;

    private List<DiscountCourseDTO> discountCourses;

    private List<FavouriteDTO> favourites;
}
