package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.FavouriteDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.exceptions.DataNotFoundException;
import com.project.learnifyapp.service.impl.FavouriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FavouriteController {

    private final FavouriteService favouriteService;


    @PostMapping("/favourite")
    public ResponseEntity<FavouriteDTO> createFavourite(@Valid @RequestBody FavouriteDTO favouriteDTO){
        if (favouriteDTO.getId() != null) {
            throw new BadRequestAlertException("A new Favourite cannot already have an Id", ENTITY_NAME, "idexists");
        }

        FavouriteDTO result = favouriteService.save(favouriteDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PutMapping("/favourite/{id}")
    public ResponseEntity<FavouriteDTO> updateFavourite(@PathVariable Long id, @Valid @RequestBody FavouriteDTO favouriteDTO) throws DataNotFoundException {
        if (favouriteDTO.getId() != null && !Objects.equals(id, favouriteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!favouriteService.exitsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnot found");
        }

        FavouriteDTO result = favouriteService.update(id, favouriteDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @DeleteMapping("/favourite/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) throws DataNotFoundException {
        favouriteService.remove(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/favourite/{id}")
    public ResponseEntity<FavouriteDTO> getFavouriteById(@PathVariable Long id) throws DataNotFoundException {
        FavouriteDTO favouriteDTO = favouriteService.getFavourite(id);
        return ResponseEntity
                .ok()
                .body(favouriteDTO);
    }


    @GetMapping("/favourites")
    public ResponseEntity<List<FavouriteDTO>> getAllFavourites() {
        List<FavouriteDTO> favourites = favouriteService.getAllFavourites();
        return ResponseEntity
                .ok(favourites);
    }
}
