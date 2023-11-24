package com.project.learnifyapp.service.impl;


import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.models.Comment;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CommentRepository;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.ICommentsService;

import com.project.learnifyapp.service.mapper.CommentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService implements ICommentsService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final LessonRepository lessonRepository;

    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, LessonRepository lessonRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
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
    public CommentDTO save(CommentDTO commentDTO) {
        Comment comment = commentMapper.toEntity(commentDTO);

        Long lessonId = commentDTO.getLessonId();
        if (lessonId == null) {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + lessonId));
            comment.setLesson(lesson);
        }

        Long userId = commentDTO.getUserId();
        if (userId == null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            comment.setUser(user);
        }

        comment = commentRepository.save(comment);

        return convertToCommentDTO(comment);
    }

    @Override
    public CommentDTO update(Long commentId, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        Long lessonId = commentDTO.getLessonId();
        if (lessonId != null) {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new EntityNotFoundException("Lesson not found with id: " + lessonId));
            existingComment.setLesson(lesson);
        }

        Long userId = commentDTO.getUserId();
        if (userId != null) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
            existingComment.setUser(user);
        }

        commentRepository.save(existingComment);
        return commentDTO;
    }

    @Override
    public void remove(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        return convertToCommentDTO(comment);
    }

    @Override
    public boolean existingComment(Long commentId) {
        return commentRepository.existsById(commentId);
    }

}