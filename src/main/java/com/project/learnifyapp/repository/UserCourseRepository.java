package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long>{

    List<UserCourse> findByUserIdAndPaymentStatus(Long userId, boolean paymentStatus);

    UserCourse findOneByUserIdAndCourseIdAndPaymentStatus(Long userId, Long courseId, boolean paymentStatus);
}
