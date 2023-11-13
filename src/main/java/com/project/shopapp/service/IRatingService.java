package com.project.shopapp.service;

import com.project.shopapp.dtos.RatingDTO;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface IRatingService {

    RatingDTO addRating(RatingDTO ratingDTO);

    RatingDTO getRating(Long ratingId) throws NotFoundException;

    RatingDTO updateRating(Long ratingId, RatingDTO ratingDTO) throws NotFoundException;

    void deleteRating(Long ratingId);

    List<RatingDTO> getAllRatings();
}
