package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.courseImage WHERE c.id = :courseId")
    Optional<Course> getDetailCourse(@Param("courseId") Long courseId);

    @Query(value= "SELECT * FROM courses cs WHERE " +
            ":keyword IS NULL OR (cs.title LIKE CONCAT('%', :keyword, '%'))", nativeQuery = true)
    Page<Course> searchCategory(@Param("keyword") String keyword, PageRequest pageRequest);

    @Query(value = "SELECT courses.id as course_id, courses.thumbnail, courses.created_at,courses.title, users.fullname, user_image.image_url, sections.id as sections_id, sections.quantity_lesson " +
            "FROM courses " +
            "LEFT JOIN users ON courses.user_id = users.id " +
            "LEFT JOIN sections ON sections.course_id = courses.id " +
            "LEFT JOIN user_image ON user_image.image_url = users.image_url", nativeQuery = true)
    List<Object[]> getCoursesInfo();
}
