package com.project.shopapp.service;

import com.project.shopapp.dtos.CommentDTO;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface ICommentsService {

    CommentDTO addComment(CommentDTO commentDTO);

    CommentDTO getComment(Long commentId) throws NotFoundException;

    CommentDTO updateComment(Long commentId, CommentDTO commentDTO) throws NotFoundException;

    void deleteComment(Long commentId);

    List<CommentDTO> getAllComments();
}

