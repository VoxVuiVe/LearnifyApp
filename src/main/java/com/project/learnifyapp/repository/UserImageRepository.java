package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findByUserId(Long userId);
}
