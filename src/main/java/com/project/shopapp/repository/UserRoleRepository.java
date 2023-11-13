package com.project.shopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.shopapp.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
    
}
