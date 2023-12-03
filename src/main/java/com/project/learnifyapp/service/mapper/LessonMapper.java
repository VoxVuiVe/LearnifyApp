package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.models.Lesson;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {SectionMapper.class})
public interface LessonMapper extends EntityMapper<LessonDTO, Lesson> {
    @Override
    @Mapping(source = "section.id", target = "sectionId")
    LessonDTO toDTO(Lesson entity);

    @Override
    @Mapping(source = "sectionId", target = "section.id")
    Lesson toEntity(LessonDTO dto);
}
