package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.RatingDTO;
import com.project.learnifyapp.service.IRatingService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingDTO> addRating(@RequestBody @Valid RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.addRating(ratingDTO);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> getRating(@PathVariable Long ratingId) throws NotFoundException {
        RatingDTO ratingDTO = ratingService.getRating(ratingId);
        return ResponseEntity.ok(ratingDTO);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId, @RequestBody @Valid RatingDTO ratingDTO) throws NotFoundException {
        RatingDTO updatedRating = ratingService.updateRating(ratingId, ratingDTO);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RatingDTO>> getAllRatings() {
        List<RatingDTO> allRatings = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatings);
    }
}
