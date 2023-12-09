package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountService;
import com.project.learnifyapp.service.mapper.DiscountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService implements IDiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountCourseRepository discountCourseRepository;
    private final CourseRepository courseRepository;

    private final DiscountMapper discountMapper;

    @Override
    public DiscountDTO createDiscourse(DiscountDTO discountDTO) {
        Discount discount = discountMapper.toEntity(discountDTO);
        discount = discountRepository.save(discount);
        return discountMapper.toDTO(discount);
    }

    @Override
    public Optional<DiscountDTO> getDiscourseById(long id) {
        return discountRepository.findById(id).map(discountMapper::toDTO);
    }

    @Override
    public Page<Discount> getDiscountPage(PageRequest pageRequest) {
        return discountRepository.findAll(pageRequest);
    }

    @Override
    public List<DiscountDTO> getAllDiscount() {
        return discountRepository.findAll().stream().map(discountMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public Discount findName(String code) {
        return discountRepository.findByCode(code);
    }

    @Override
    public void deleteDiscourse(long id) {
//        long discount = discountCourseRepository.existsByDiscount(id);
//        Optional<Discount> discount =  discountRepository.findById(id);
        discountRepository.deleteById(id);
    }
}
//
//    @Override
//    public boolean existsByName(String name) {
//        return false;
//    }

