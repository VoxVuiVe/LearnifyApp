package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommnetRepository extends JpaRepository<Comment, Long>{
    
}
