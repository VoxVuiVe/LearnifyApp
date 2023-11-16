package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class,LessonMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment>{

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "lesson.id", target = "lessonId")
    CommentDTO toDTO(Comment entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "lessonId", target = "lesson.id")
    Comment toEntity(CommentDTO dto);
}
