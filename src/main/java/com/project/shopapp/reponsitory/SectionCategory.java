package com.project.shopapp.reponsitory;

import com.project.shopapp.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionCategory extends JpaRepository<Section, Long> {
}
