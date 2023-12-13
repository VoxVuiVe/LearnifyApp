package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Modifying
    @Query(value = "UPDATE sections SET is_delete = :isDelete WHERE id = :id", nativeQuery = true)
    void updateIsDeleteById(@Param("id") Long id, @Param("isDelete") Boolean isDelete);
}
