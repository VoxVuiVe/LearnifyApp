package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
