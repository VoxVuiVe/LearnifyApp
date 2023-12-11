package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.CartItem;
import com.project.learnifyapp.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByIsDeleteTrue();
    Optional<Course> findByIdAndIsDeleteFalse(Long id);
}
