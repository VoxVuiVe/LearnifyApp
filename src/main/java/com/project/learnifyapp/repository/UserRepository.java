package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); //kiem tra user co email co ton tai hay khong

    Optional<User> findByEmail(String email);
}
