package com.project.learnifyapp.service.impl;


import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.models.Comment;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CommnetRepository;
import com.project.learnifyapp.service.ICommentsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentsService {

    private final CommnetRepository commentRepository;

    @Autowired
    public CommentService (CommnetRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUserId(comment.getUser().getUserId());
        commentDTO.setCourseId(comment.getCourse().getCourseId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreateDate(comment.getCreateDate());
        commentDTO.setNumberOfLikeComments(comment.getNumber_of_like_comments());
        return commentDTO;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        // Set user and course based on IDs from DTO
        comment.setUser(new User());
        comment.setCourse(new Course());
        comment.setContent(commentDTO.getContent());
        comment.setCreateDate(commentDTO.getCreateDate());
        comment.setNumber_of_like_comments(commentDTO.getNumberOfLikeComments());

        Comment savedComment = commentRepository.save(comment);
        return convertToCommentDTO(savedComment);
    }

    @Override
    public CommentDTO getComment(Long commentId) throws NotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException());
        return convertToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO) throws NotFoundException {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException());

        // Update fields
        existingComment.setUser(new User());
        existingComment.setCourse(new Course());
        existingComment.setContent(commentDTO.getContent());
        existingComment.setCreateDate(commentDTO.getCreateDate());
        existingComment.setNumber_of_like_comments(commentDTO.getNumberOfLikeComments());

        Comment updatedComment = commentRepository.save(existingComment);
        return convertToCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
    }
}

