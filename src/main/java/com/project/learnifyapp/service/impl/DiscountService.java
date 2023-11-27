package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountService;
import com.project.learnifyapp.service.mapper.DiscountMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class DiscountService implements IDiscountService {
    private final DiscountRepository discountRepository;
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
    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    @Override
    public void deleteDiscourse(long id) {
        discountRepository.deleteById(id);
    }
//
//    @Override
//    public boolean existsByName(String name) {
//        return false;
//    }
}
