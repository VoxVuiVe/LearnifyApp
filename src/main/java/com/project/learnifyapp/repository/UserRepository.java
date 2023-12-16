package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); //kiem tra user co email co ton tai hay khong

    Optional<User> findByEmail(String email);
    //SELECT * FROM users WHERE email = ?

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userImage WHERE u.id = :userId")
    Optional<User> getDetailUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM users u WHERE " +
            "(:keyword IS NULL OR (u.fullname LIKE CONCAT('%', :keyword, '%')) OR (u.email LIKE CONCAT('%',:keyword,'%')))", nativeQuery = true)
    Page<User> searchUsers(@Param("keyword") String keyword, PageRequest pageRequest);

//GetUserTeacher & TotalCourse
@Query(value = "SELECT u.id, u.fullname, (SELECT image_url FROM user_image ui WHERE user_id = u.id LIMIT 1) AS user_image_url, COUNT(c.id) AS quantity_course " +
        "FROM users u " +
        "LEFT JOIN courses c ON c.user_id = u.id " +
        "WHERE u.role_id = 2 " +
        "GROUP BY u.id, u.fullname", nativeQuery = true)
    List<Object[]> getUserTeacherInfo();
}
