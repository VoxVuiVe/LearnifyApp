package com.project.learnifyapp.service.mapper;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class,CourseMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment>{

    @Override
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "course.id", target = "courseId")
    CommentDTO toDTO(Comment entity);

    @Override
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "courseId", target = "course.id")
    Comment toEntity(CommentDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    void updateCommentFromDTO(CommentDTO commentDTO, @MappingTarget Comment comment);
}
