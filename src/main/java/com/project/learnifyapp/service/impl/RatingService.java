package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.RatingDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Rating;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.RatingRepositoty;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IRatingService;

import com.project.learnifyapp.service.mapper.RatingMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    private final Logger log = LoggerFactory.getLogger(CommentsService.class);

    private final RatingRepositoty ratingRepositoty;

    private final RatingMapper ratingMapper;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    @Override
    public RatingDTO save(RatingDTO ratingDTO) throws DataNotFoundException {
        log.debug("Save new Rating: {}", ratingDTO);

        Course course = courseRepository.findById(ratingDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(ratingDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Rating rating = ratingMapper.toEntity(ratingDTO);
        rating.setCourse(course);
        rating.setUser(user);

        Rating saveRating = ratingRepositoty.save(rating);

        log.debug("New rating saved: {}", saveRating);

        return ratingMapper.toDTO(saveRating);
    }

    @Override
    public RatingDTO update(Long Id, RatingDTO ratingDTO) throws DataNotFoundException {
        log.debug("Update rating with ID: {}", Id);

        Rating exitingRating = ratingRepositoty.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Rating not found"));

        Course course = courseRepository.findById(ratingDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(ratingDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        ratingMapper.updateRatingFromDTO(ratingDTO, exitingRating);
        exitingRating.setCourse(course);
        exitingRating.setUser(user);

        Rating updateRating = ratingRepositoty.save(exitingRating);

        log.debug("Update rating: {}", updateRating);

        return ratingMapper.toDTO(updateRating);
    }

    @Override
    public RatingDTO remove(Long Id) throws DataNotFoundException {
        log.debug("Remove rating with ID: {}", Id);

        Rating rating = ratingRepositoty.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Rating not found"));

        ratingRepositoty.delete(rating);

        log.debug("Rating remove: {}", rating);

        return ratingMapper.toDTO(rating);
    }

    public RatingDTO getRating(Long Id) throws DataNotFoundException {
        log.debug("Fetching comment with ID: {}", Id);

        Rating rating = ratingRepositoty.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Rating not found"));

        log.debug("Fetched rating: {}", rating);

        return ratingMapper.toDTO(rating);
    }

    @Override
    public List<RatingDTO> getAllRatings() {
        return ratingRepositoty.findAll().stream().map(ratingMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return ratingRepositoty.existsById(id);
    }
}
