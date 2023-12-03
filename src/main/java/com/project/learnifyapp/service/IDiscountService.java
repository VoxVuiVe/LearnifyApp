package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;


public interface IDiscountService {
    DiscountDTO createDiscourse(DiscountDTO discountDTO);
    Optional<DiscountDTO> getDiscourseById(long id);
    Page<Discount> getDiscountPage (PageRequest pageRequest);

    List<DiscountDTO> getAllDiscount();

    void deleteDiscourse(long id);
//    boolean existsByName(String name);
}
