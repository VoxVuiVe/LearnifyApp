package com.project.learnifyapp.controllers;


import java.util.List;
import java.util.Objects;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.service.impl.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.learnifyapp.dtos.CommentDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;


import jakarta.validation.Valid;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentsService commentsService;

    @PostMapping("/comment")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO){
        if (commentDTO.getId() != null) {
            throw new BadRequestAlertException("A new Comment cannot already have an Id", ENTITY_NAME, "idexists");
        }
        CommentDTO result = commentsService.save(commentDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDTO commentDTO) throws DataNotFoundException {
        if (commentDTO.getId() != null && !Objects.equals(id, commentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!commentsService.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CommentDTO result = commentsService.update(id, commentDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) throws DataNotFoundException {
        commentsService.remove(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id){
        CommentDTO commentDTO = commentsService.getComment(id);
        return ResponseEntity
                .ok()
                .body(commentDTO);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comments = commentsService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}


