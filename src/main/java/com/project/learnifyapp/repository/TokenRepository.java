package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Token;
import com.project.learnifyapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
}
