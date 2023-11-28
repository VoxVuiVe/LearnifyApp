package com.project.learnifyapp.service;

import com.project.learnifyapp.dtos.FavouriteDTO;

import java.util.List;

import com.project.learnifyapp.exceptions.DataNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface IFavouriteService {

    FavouriteDTO save(FavouriteDTO favouriteDTO) throws DataNotFoundException;

    FavouriteDTO getFavourite(Long favouriteId) throws NotFoundException, DataNotFoundException;

    FavouriteDTO update(Long favouriteId, FavouriteDTO favouriteDTO) throws DataNotFoundException;

    void remove(Long favouriteId) throws DataNotFoundException;

    List<FavouriteDTO> getAllFavourites();
}
