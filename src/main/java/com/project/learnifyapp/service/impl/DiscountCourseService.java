package com.project.learnifyapp.service.impl;
import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.models.DiscountCourse;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountCourseService;
import com.project.learnifyapp.service.mapper.DiscountCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCourseService implements IDiscountCourseService {
    private final DiscountRepository discountRepository;
    private final CourseRepository courseRepository;
    private final DiscountCourseRepository discountCourseRepository;
    private final DiscountCourseMapper discountCourseMapper;


    @Override
    public DiscountCourseDTO createDiscountCourse(DiscountCourseDTO discountCourseDTO) {
        boolean discountId = discountRepository.existsById(discountCourseDTO.getDiscountId());
        boolean courseId = courseRepository.existsById(discountCourseDTO.getCourseId());
        if(discountId == false || courseId == false){
            return null;
        }
        DiscountCourse discountCourse = discountCourseMapper.toEntity(discountCourseDTO);
        discountCourse = discountCourseRepository.save(discountCourse);
        return discountCourseMapper.toDTO(discountCourse);
    }
}
