package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {

    @Query(value= "SELECT * FROM discounts disc WHERE " +
            ":keyword IS NULL OR (disc.code LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<Discount> searchCategory(@Param("keyword") String keyword, PageRequest pageRequest);

    Discount findByCode(String code);
}
