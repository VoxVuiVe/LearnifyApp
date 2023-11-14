package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Long>{
}
