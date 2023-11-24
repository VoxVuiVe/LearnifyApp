package com.project.learnifyapp.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.repository.CommentRepository;
import com.project.learnifyapp.service.impl.CommentService;
import com.project.learnifyapp.service.impl.LessonService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private LessonService lessonService;

    public CommentsController(
            CommentService commentService,
            CommentRepository commentRepository,
            LessonService lessonService
    ) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.lessonService = lessonService;
    }

    @PostMapping("")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        // kiểm tra lessonId có giá trị và có tồn tại kh
        if (commentDTO.getLessonId() == null || commentDTO.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommentDTO());
        }

//        if(lessonService.isLessonExists(commentDTO.getLessonId())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommentDTO());
//        }
//
//        if(commentDTO.getId() != null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommentDTO());
//        }

        CommentDTO result = commentService.save(commentDTO);
        return ResponseEntity
                .created(new URI("/api/comments/" + result.getId()))
                .body(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "id", required = false) Long id, @Valid @RequestBody CommentDTO commentDTO)
            throws NotFoundException {
        if (commentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "comment", "idnull");
        }

        if (!commentService.existingComment(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (!Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", "comment", "idinvalid");
        }

        CommentDTO result = commentService.update(id, commentDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        if (!commentService.existingComment(id)) {
            return ResponseEntity.notFound().build();
        }

        commentService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable Long id) {
        if (!commentService.existingComment(id)) {
            return ResponseEntity.notFound().build();
        }

        CommentDTO commentDTO = commentService.getComment(id);
        return ResponseEntity.ok(commentDTO);
    }
}