package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface DiscountCourseRepository extends JpaRepository<DiscountCourseRepository, Long> {
}
