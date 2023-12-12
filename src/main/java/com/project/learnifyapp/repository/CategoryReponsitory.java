package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Long> {
    @Query(value= "SELECT * FROM categories cate WHERE " +
            ":keyword IS NULL OR (cate.name LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<Category> searchCategory(@Param("keyword") String keyword, PageRequest pageRequest);
}
