package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.project.learnifyapp.models.Comment;

@EnableJpaRepositories
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
