package com.project.learnifyapp.repository;

import com.project.learnifyapp.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SectionRepository extends JpaRepository<Section, Long> {
}
