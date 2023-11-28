package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.learnifyapp.models.DiscountCourse;

@SuppressWarnings("unused")
@Repository
public interface DiscountCourseRepository extends JpaRepository<DiscountCourse, Long> {
    @Query(value = "select dc.discount_id from discount_courses dc where dc.discount_id = :id", nativeQuery = true)
    Long findByDiscountId(@Param("id") Long id);

    @Query(value = "delete from discount_courses dc where dc.discount_id = :id", nativeQuery = true)
    @Modifying
    void deleteAllDiscountId(@Param("id") Long id);
}