package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    List<UserImage> findByUserId(Long userId);

    @Query(value = "delete from learnifyapp.user_image ui where ui.user_id = :userId", nativeQuery = true)
    @Modifying
    void deleteImageByUserId(@Param("userId") Long userId);

    @Query(value = "update learnifyapp.user u set u.is_active = false where u.id = :userId", nativeQuery = true)
    @Modifying
    void updateIsActiveUser(@Param("userId") Long userId);
}
