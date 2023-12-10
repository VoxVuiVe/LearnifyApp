package com.project.learnifyapp.service.impl;
import com.project.learnifyapp.dtos.DiscountCourseDTO;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.DiscountCourse;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountCourseService;
import com.project.learnifyapp.service.mapper.DiscountCourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.ExportException;
import java.util.Optional;

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

    @Override
    @Transactional
    public void deleteDiscountCourse(Long id) {
        boolean check = discountCourseRepository.existsById(id);
        if(check == true){
            try {
                discountCourseRepository.deleteDiscountCourseById(id);
                System.out.println("Xóa thành công");
            } catch (Exception e) {
                System.out.println("Lỗi khi xóa: " + e.getMessage());
            }
        }
    }
}
