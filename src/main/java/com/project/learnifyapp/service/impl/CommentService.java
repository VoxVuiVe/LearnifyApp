package com.project.learnifyapp.service.impl;


import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.models.Comment;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Lesson;
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
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setLessonId(comment.getLesson().getId());
        commentDTO.setComment(comment.getComment());
        commentDTO.setCreateDate(comment.getCreateDate());
        commentDTO.setIsLike(comment.getIsLike());
        commentDTO.setQuantity(comment.getQuantity());
        return commentDTO;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        // Set user and course based on IDs from DTO
        comment.setUser(new User());
        comment.setLesson(new Lesson());
        comment.setComment(commentDTO.getComment());
        comment.setCreateDate(commentDTO.getCreateDate());
        comment.setCreateDate(commentDTO.getCreateDate());
        comment.setIsLike(commentDTO.getIsLike());
        comment.setQuantity(commentDTO.getQuantity());

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
        existingComment.setLesson(new Lesson());
        existingComment.setComment(commentDTO.getComment());
        existingComment.setCreateDate(commentDTO.getCreateDate());
        existingComment.setCreateDate(commentDTO.getCreateDate());
        existingComment.setIsLike(commentDTO.getIsLike());
        existingComment.setQuantity(commentDTO.getQuantity());
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

