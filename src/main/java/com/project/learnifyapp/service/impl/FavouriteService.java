package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Favourite;
import com.project.learnifyapp.models.Lesson;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.CourseRepository;
import com.project.learnifyapp.repository.FavouriteRepository;
import com.project.learnifyapp.repository.LessonRepository;
import com.project.learnifyapp.repository.UserRepository;
import com.project.learnifyapp.service.IFavouriteService;
import com.project.learnifyapp.service.mapper.FavouriteMapper;
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
public class FavouriteService implements IFavouriteService {

    private final Logger log = LoggerFactory.getLogger(FavouriteService.class);

    private final FavouriteRepository favouriteRepository;

    private final FavouriteMapper favouriteMapper;

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;


    @SneakyThrows
    @Override
    public FavouriteDTO save(FavouriteDTO favouriteDTO){
        log.debug("Save new favourite: {}", favouriteDTO);

        Course course = courseRepository.findById(favouriteDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(favouriteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        Favourite favourite = favouriteMapper.toEntity(favouriteDTO);
        favourite.setCourse(course);
        favourite.setUser(user);

        Favourite saveFavourite = favouriteRepository.save(favourite);

        log.debug("New favourite saved: {}", saveFavourite);

        return favouriteMapper.toDTO(saveFavourite);
    }

    @Override
    public FavouriteDTO update(Long Id, FavouriteDTO favouriteDTO) throws DataNotFoundException {
        log.debug("Update favourite with ID: {}", Id);

        Favourite exitsingFavourite = favouriteRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Favourite not found"));

        Course course = courseRepository.findById(favouriteDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Course not found"));

        User user = userRepository.findById(favouriteDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        favouriteMapper.updateFavouriteFromDTO(favouriteDTO, exitsingFavourite);
        exitsingFavourite.setCourse(course);
        exitsingFavourite.setUser(user);

        Favourite updateFavourite = favouriteRepository.save(exitsingFavourite);

        log.debug("Update favourite: {}", updateFavourite);

        return favouriteMapper.toDTO(updateFavourite);
    }

    @Override
    public void remove(Long Id) throws DataNotFoundException {
        log.debug("Remove favourite with ID: {}", Id);

        Favourite favourite = favouriteRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Favourite not found"));

        favouriteRepository.delete(favourite);

        log.debug("Favourite remove: {}", favourite);
    }

    @Override
    public FavouriteDTO getFavourite(Long Id) throws DataNotFoundException {
        log.debug("Fetching favourite with ID: {}", Id);

        Favourite favourite = favouriteRepository.findById(Id)
                .orElseThrow(() -> new DataNotFoundException("Favourite not found"));

        log.debug("Fetched favourite: {}", favourite);

        return favouriteMapper.toDTO(favourite);
    }

    @Override
    public List<FavouriteDTO> getAllFavourites() {
        return favouriteRepository.findAll().stream().map(favouriteMapper::toDTO).collect(Collectors.toList());
    }

    public boolean exitsById(Long id) {
        return favouriteRepository.existsById(id);
    }
}
