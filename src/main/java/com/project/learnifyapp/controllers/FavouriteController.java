package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.service.impl.FavouriteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @Autowired
    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }

    @PostMapping
    public ResponseEntity<FavouriteDTO> addFavourite(@RequestBody @Valid FavouriteDTO favouriteDTO) {
        FavouriteDTO savedFavourite = favouriteService.addFavourite(favouriteDTO);
        return new ResponseEntity<>(savedFavourite, HttpStatus.CREATED);
    }

    @GetMapping("/{favouriteId}")
    public ResponseEntity<FavouriteDTO> getFavourite(@PathVariable Long favouriteId) throws NotFoundException {
        FavouriteDTO favouriteDTO = favouriteService.getFavourite(favouriteId);
        return ResponseEntity.ok(favouriteDTO);
    }

    @PutMapping("/{favouriteId}")
    public ResponseEntity<FavouriteDTO> updateFavourite(@PathVariable Long favouriteId, @RequestBody @Valid FavouriteDTO favouriteDTO) throws NotFoundException {
        FavouriteDTO updatedFavourite = favouriteService.updateFavourite(favouriteId, favouriteDTO);
        return ResponseEntity.ok(updatedFavourite);
    }

    @DeleteMapping("/{favouriteId}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long favouriteId) {
        favouriteService.deleteFavourite(favouriteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<FavouriteDTO>> getAllFavourites() {
        List<FavouriteDTO> allFavourites = favouriteService.getAllFavourites();
        return ResponseEntity.ok(allFavourites);
    }
}
