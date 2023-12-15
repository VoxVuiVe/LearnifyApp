package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value= "SELECT * FROM courses cs WHERE " +
            ":keyword IS NULL OR (cs.title LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<Course> searchCategory(@Param("keyword") String keyword, PageRequest pageRequest);
}
