package com.project.shopapp.repository;

import com.project.shopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReponsitory extends JpaRepository<Category, Long> {

}
