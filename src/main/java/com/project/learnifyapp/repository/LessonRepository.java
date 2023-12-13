package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT * FROM learnifyapp.lessons where section_id = :id", nativeQuery = true)
    Lesson findBySectionId(@Param("id") Long id);

}
