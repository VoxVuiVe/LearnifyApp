package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.CourseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImage, Long> {
    List<CourseImage> findByCourseId(Long courseId);
}
