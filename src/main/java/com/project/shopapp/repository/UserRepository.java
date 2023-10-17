package com.project.shopapp.repository;

import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); //kiem tra ng user co phoneNumber co ton tai hay khong

    Optional<User> findByEmail(String email);
}
