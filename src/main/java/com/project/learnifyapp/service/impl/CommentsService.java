package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Comment;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CommentRepository;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.ICommentsService;
import com.project.learnifyapp.service.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService implements ICommentsService {

    private final Logger log = LoggerFactory.getLogger(CommentsService.class);

    private final CommentRepository commentRepository;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDTO save(CommentDTO commentDTO) throws DataNotFoundException {
        log.debug("Save new comment: {}", commentDTO);
        Course course = courseRepository.findById(commentDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Comment comment = commentMapper.toEntity(commentDTO);
//        comment.setQuantity(user.getComments() * comment.getQuantity());
        comment.setCourse(course);
        comment.setUser(user);

        Comment saveComment = commentRepository.save(comment);

        log.debug("New comment saved: {}", saveComment);

        return commentMapper.toDTO(saveComment);
    }

    @Override
    public CommentDTO update(Long Id, CommentDTO commentDTO) throws DataNotFoundException {
        log.debug("Update comment with ID: {}", Id);

        Comment existingComment = commentRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found"));

        Course course = courseRepository.findById(commentDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        commentMapper.updateCommentFromDTO(commentDTO, existingComment);
        existingComment.setCourse(course);
        existingComment.setUser(user);

        Comment updatedComment = commentRepository.save(existingComment);

        log.debug("Updated comment: {}", updatedComment);

        return commentMapper.toDTO(updatedComment);
    }

    @Override
    public void remove(Long Id) throws DataNotFoundException {
        log.debug("Remove comment with ID: {}", Id);

        Comment comment = commentRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found"));

        commentRepository.delete(comment);

        log.debug("Comment removed: {}", comment);
    }

    @Override
    public CommentDTO getComment(Long Id) throws DataNotFoundException {
        log.debug("Fetching comment with ID: {}", Id);

        Comment comment = commentRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found"));

        log.debug("Fetched comment: {}", comment);

        return commentMapper.toDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream().map(commentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllCommentByCourseId(Long courseId) {
        return commentRepository.getCommentByCourseId(courseId).stream().map(commentMapper::toDTO).collect(Collectors.toList());
    }

    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }
}



