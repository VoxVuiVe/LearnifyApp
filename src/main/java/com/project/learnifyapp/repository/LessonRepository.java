package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
