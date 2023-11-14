package com.project.learnifyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.learnifyapp.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
}
