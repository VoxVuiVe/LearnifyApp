package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.models.Discount;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiscountController {
    private final IDiscountService discountService;
    private final DiscountRepository discountRepository;
    @PostMapping("/discounts")
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountDTO discountDTO){
        if(discountDTO.getId() != null){
            throw new BadRequestAlertException("A new Discount cannot already have an Id",ENTITY_NAME, "idexists");
        }
        DiscountDTO result = discountService.createDiscourse(discountDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/discounts/{id}")
    public ResponseEntity<DiscountDTO> updateDiscount(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody DiscountDTO discountDTO){
        if(discountDTO.getId() == null){
            throw new BadRequestAlertException("Invalid id",ENTITY_NAME,"idnull");
        }
        if (!Objects.equals(id, discountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!discountRepository
                .existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscountDTO result = discountService.createDiscourse(discountDTO);
        return  ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable Long id){
        Optional<DiscountDTO> discountDTO = discountService.getDiscourseById(id);
        if (discountDTO.isPresent()){
            return new ResponseEntity<>(discountDTO.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/discounts/{id}")
    public void deleteDiscourse(@PathVariable long id){
        discountService.deleteDiscourse(id);
    }

    @GetMapping("/discounts/pages")
    public ResponseEntity<Page<Discount>> getDiscountsPage(
            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Discount> discounts = discountService.getDiscountPage(pageRequest);
        return ResponseEntity.ok().body(discounts);
    }

    @GetMapping("/discounts")
    public ResponseEntity<List<Discount>> getAllDiscount(){
        List<Discount> discounts = discountService.getAllDiscount();
        return ResponseEntity.ok().body(discounts);
    }
}
