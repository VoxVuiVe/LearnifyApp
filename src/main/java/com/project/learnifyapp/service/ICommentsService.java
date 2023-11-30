package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.CommentDTO;
import java.util.List;
import com.project.learnifyapp.exceptions.DataNotFoundException;

public interface ICommentsService {

    CommentDTO save(CommentDTO commentDTO) throws DataNotFoundException;

    CommentDTO update(Long Id, CommentDTO commentDTO) throws DataNotFoundException;

    void remove(Long Id) throws DataNotFoundException;

    CommentDTO getComment(Long Id) throws DataNotFoundException;

    List<CommentDTO> getAllComments();

    boolean existsById(Long id);

}

