package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CommentDTO;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface ICommentsService {

    CommentDTO save(CommentDTO commentDTO);

    CommentDTO getComment(Long commentId);

    CommentDTO update(Long commentId, CommentDTO commentDTO);

    void remove(Long commentId);

    boolean existingComment(Long commentId);
}

