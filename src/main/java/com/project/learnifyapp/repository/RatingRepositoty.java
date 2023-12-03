package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.Rating;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepositoty extends JpaRepository<Rating, Long>{

}
