package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.LessonDTO;
import com.project.learnifyapp.models.Lesson;
import org.mapstruct.*;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D dtos);

    D toDTO(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDTO(List<E> entityList);

}