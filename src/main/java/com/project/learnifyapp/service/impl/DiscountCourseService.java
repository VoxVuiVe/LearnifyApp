package com.project.learnifyapp.service.impl;
import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.models.DiscountCourse;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.service.IDiscountCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCourseService implements IDiscountCourseService {
    private final DiscountCourseRepository discountCourseRepository;
    @Override
    public DiscountCourse createDiscountCourse(DiscountCourseDTO discountCourseDTO) {
        DiscountCourse newDiscountCourse = DiscountCourse.builder()
                .course(discountCourseDTO.getCourse())
                .discount(discountCourseDTO.getDiscount())
                .build();
            newDiscountCourse = discountCourseRepository.save(newDiscountCourse);
        return newDiscountCourse;
    }
}
