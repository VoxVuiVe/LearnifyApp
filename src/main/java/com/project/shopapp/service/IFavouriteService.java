package com.project.shopapp.service;

import com.project.shopapp.dtos.FavouriteDTO;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface IFavouriteService {

    FavouriteDTO addFavourite(FavouriteDTO favouriteDTO);

    FavouriteDTO getFavourite(Long favouriteId) throws NotFoundException;

    FavouriteDTO updateFavourite(Long favouriteId, FavouriteDTO favouriteDTO) throws NotFoundException;

    void deleteFavourite(Long favouriteId);

    List<FavouriteDTO> getAllFavourites();
}
