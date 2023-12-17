package com.project.learnifyapp.controllers;


import com.project.learnifyapp.dtos.RatingDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.service.impl.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("${api.prefix}/")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/rating")
    public ResponseEntity<RatingDTO> createRating(@Valid @RequestBody RatingDTO ratingDTO) throws BadRequestAlertException, DataNotFoundException {
        if (ratingDTO.getId() != null) {
            throw new BadRequestAlertException("A new rating cannot already have an Id", ENTITY_NAME, "id exits");
        }
        RatingDTO result = ratingService.save(ratingDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/rating/{id}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody RatingDTO ratingDTO) throws DataNotFoundException {
        if (ratingDTO.getId() != null && !Objects.equals(id, ratingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "id invalid");
        }

        if (!ratingService.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }

        RatingDTO result = ratingService.update(id, ratingDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/rating/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) throws DataNotFoundException {
        ratingService.remove(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<RatingDTO> getRatingById(@PathVariable Long id) throws DataNotFoundException {
        RatingDTO ratingDTO = ratingService.getRating(id);
        return ResponseEntity
                .ok()
                .body(ratingDTO);
    }

    @GetMapping("/ratings")
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<RatingDTO> ratings = ratingService.getAllRatings();
        return ResponseEntity
                .ok(ratings);
    }

    @GetMapping("/ratings/{courseId}")
    public ResponseEntity<List<RatingDTO>> getAllRatingByCourseId(@PathVariable Long courseId){
        List<RatingDTO> ratings = ratingService.getAllRatingsByCourseId(courseId);
        return ResponseEntity.ok(ratings);
    }
}

























