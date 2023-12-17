package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.RatingDTO;

import java.util.List;

import com.project.learnifyapp.exceptions.DataNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface IRatingService {

    RatingDTO save(RatingDTO ratingDTO) throws DataNotFoundException;

    RatingDTO getRating(Long ratingId) throws NotFoundException, DataNotFoundException;

    RatingDTO update(Long ratingId, RatingDTO ratingDTO) throws NotFoundException, DataNotFoundException;

    RatingDTO remove(Long ratingId) throws DataNotFoundException;

    List<RatingDTO> getAllRatings();

    List<RatingDTO> getAllRatingsByCourseId(Long courseId);

    boolean existsById(Long id);


}
