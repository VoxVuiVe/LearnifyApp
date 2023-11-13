package com.project.shopapp.service.impl;

import com.project.shopapp.dtos.RatingDTO;
import com.project.shopapp.models.Course;
import com.project.shopapp.models.Rating;
import com.project.shopapp.models.User;
import com.project.shopapp.repository.RatingRepositoty;
import com.project.shopapp.service.IRatingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService implements IRatingService {

    private final RatingRepositoty ratingRepository;

    @Autowired
    public RatingService (RatingRepositoty ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    private RatingDTO convertToRatingDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setUserId(rating.getUser().getUserId());
        ratingDTO.setCourseId(rating.getCourse().getCourseId());
        ratingDTO.setRating(rating.getRating());
        ratingDTO.setContent(rating.getContent());
        ratingDTO.setCreateDate(rating.getCreateDate());
        return ratingDTO;
    }

    @Override
    public RatingDTO addRating(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        // Set user and course based on IDs from DTO
        rating.setUser(new User());
        rating.setCourse(new Course());
        rating.setRating(ratingDTO.getRating());
        rating.setContent(ratingDTO.getContent());
        rating.setCreateDate(ratingDTO.getCreateDate());

        Rating savedRating = ratingRepository.save(rating);
        return convertToRatingDTO(savedRating);
    }

    @Override
    public RatingDTO getRating(Long ratingId) throws NotFoundException {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NotFoundException());
        return convertToRatingDTO(rating);
    }

    @Override
    public RatingDTO updateRating(Long ratingId, RatingDTO ratingDTO) throws NotFoundException {
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NotFoundException());

        // Update fields
        existingRating.setUser(new User());
        existingRating.setCourse(new Course());
        existingRating.setRating(ratingDTO.getRating());
        existingRating.setContent(ratingDTO.getContent());
        existingRating.setCreateDate(ratingDTO.getCreateDate());

        Rating updatedRating = ratingRepository.save(existingRating);
        return convertToRatingDTO(updatedRating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public List<RatingDTO> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(this::convertToRatingDTO)
                .collect(Collectors.toList());
    }
}
