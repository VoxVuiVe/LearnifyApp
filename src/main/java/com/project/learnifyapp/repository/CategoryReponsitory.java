package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Long> {
}
