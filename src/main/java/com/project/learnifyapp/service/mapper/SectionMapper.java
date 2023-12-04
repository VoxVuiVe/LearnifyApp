package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.dtos.SectionDTO;
import com.project.learnifyapp.models.Favourite;
import com.project.learnifyapp.models.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SectionMapper extends EntityMapper<SectionDTO, Section> {

    @Override
    @Mapping(source = "course.id", target = "courseId")
    SectionDTO toDTO(Section entity);

    @Override
    @Mapping(source = "courseId", target = "course.id")
    Section toEntity(SectionDTO dto);

    void updateSectionFromDTO(SectionDTO sectionDTO, @MappingTarget Section section);
}