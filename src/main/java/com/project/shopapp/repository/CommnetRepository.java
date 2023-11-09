package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.Comment;

public interface CommnetRepository extends JpaRepository<Comment, Long>{
    
}
