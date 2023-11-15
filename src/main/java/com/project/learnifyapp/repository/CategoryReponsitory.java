package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReponsitory extends JpaRepository<Category, Long> {

}
