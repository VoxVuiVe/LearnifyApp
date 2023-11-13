package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Rating;

public interface RatingRepositoty extends JpaRepository<Rating, Long>{

}
