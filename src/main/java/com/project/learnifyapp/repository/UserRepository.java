package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); //kiem tra user co email co ton tai hay khong

    Optional<User> findByEmail(String email);
    //SELECT * FROM users WHERE email = ?

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userImage WHERE u.id = :userId")
    Optional<User> getDetailUser(@Param("userId") Long userId);
}
