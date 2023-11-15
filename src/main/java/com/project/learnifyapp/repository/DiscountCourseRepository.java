package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.learnifyapp.models.DiscountCourse;

@SuppressWarnings("unused")
@Repository
public interface DiscountCourseRepository extends JpaRepository<DiscountCourse, Long> {
}
