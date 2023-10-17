package com.project.shopapp.repository;

import com.project.shopapp.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonCategory extends JpaRepository<Lesson, Long> {

}
