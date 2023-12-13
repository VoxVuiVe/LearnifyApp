package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Query(value= "SELECT * FROM Lessons ls WHERE " +
            ":keyword IS NULL OR (ls.title LIKE CONCAT('%', :keyword, '%'))" , nativeQuery = true)
    Page<Lesson> searchLesson(@Param("keyword") String keyword, PageRequest pageRequest);

    @Query(value = "SELECT * FROM learnifyapp.lessons where section_id = :id", nativeQuery = true)
    Lesson findBySectionId(@Param("id") Long id);

}
