package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.courseImage WHERE c.id = :courseId")
    Optional<Course> getDetailCourse(@Param("courseId") Long courseId);

    @Query(value= "SELECT * FROM courses cs WHERE " +
            ":keyword IS NULL OR (cs.title LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<Course> searchCategory(@Param("keyword") String keyword, PageRequest pageRequest);
}
