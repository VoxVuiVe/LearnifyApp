package com.project.learnifyapp.service.impl;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.models.Course;
import com.project.learnifyapp.models.Favourite;
import com.project.learnifyapp.models.User;
import com.project.learnifyapp.repository.FavouriteRepository;
import com.project.learnifyapp.service.IFavouriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteService implements IFavouriteService {

    private final FavouriteRepository favouriteRepository;

    @Autowired
    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    private FavouriteDTO convertToFavouriteDTO(Favourite favourite) {
        FavouriteDTO favouriteDTO = new FavouriteDTO();
        favouriteDTO.setIsActive(favourite.getIsActive());
        favouriteDTO.setUserId(favourite.getUser().getId());
        favouriteDTO.setCourseId(favourite.getCourse().getId());
        return favouriteDTO;
    }

    @Override
    public FavouriteDTO addFavourite(FavouriteDTO favouriteDTO) {
        Favourite favourite = new Favourite();
        favourite.setIsActive(favouriteDTO.getIsActive());
        // Set user and course based on IDs from DTO
        favourite.setUser(new User());
        favourite.setCourse(new Course());

        Favourite savedFavourite = favouriteRepository.save(favourite);
        return convertToFavouriteDTO(savedFavourite);
    }

    @Override
    public FavouriteDTO getFavourite(Long favouriteId) throws NotFoundException {
        Favourite favourite = favouriteRepository.findById(favouriteId)
                .orElseThrow(() -> new NotFoundException());
        return convertToFavouriteDTO(favourite);
    }

    @Override
    public FavouriteDTO updateFavourite(Long favouriteId, FavouriteDTO favouriteDTO) throws NotFoundException {
        Favourite existingFavourite = favouriteRepository.findById(favouriteId)
                .orElseThrow(() -> new NotFoundException());

        existingFavourite.setIsActive(favouriteDTO.getIsActive());
        // Update user and course based on IDs from DTO
        existingFavourite.setUser(new User());
        existingFavourite.setCourse(new Course());

        Favourite updatedFavourite = favouriteRepository.save(existingFavourite);
        return convertToFavouriteDTO(updatedFavourite);
    }

    @Override
    public void deleteFavourite(Long favouriteId) {
        favouriteRepository.deleteById(favouriteId);
    }

    @Override
    public List<FavouriteDTO> getAllFavourites() {
        List<Favourite> favourites = favouriteRepository.findAll();
        return favourites.stream()
                .map(this::convertToFavouriteDTO)
                .collect(Collectors.toList());
    }
}
