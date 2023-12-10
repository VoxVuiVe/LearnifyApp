package com.project.learnifyapp.controllers;

import com.project.learnifyapp.dtos.DiscountDTO;
import com.project.learnifyapp.exceptions.BadRequestAlertException;
import com.project.learnifyapp.models.Discount;
import com.project.learnifyapp.repository.DiscountCourseRepository;
import com.project.learnifyapp.repository.DiscountRepository;
import com.project.learnifyapp.service.IDiscountService;
import com.project.learnifyapp.service.impl.CourseService;
import com.project.learnifyapp.service.impl.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

@RestController
@RequestMapping("${api.prefix}/")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DiscountController {
    private final DiscountService discountService;
    private final DiscountRepository discountRepository;
    private final CourseService courseService;
    private final DiscountCourseRepository discountCourseRepository;
    @PostMapping("/discounts")
    public ResponseEntity<DiscountDTO> createDiscount(@Valid @RequestBody DiscountDTO discountDTO){
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
        if (!discountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscountDTO result = discountService.createDiscourse(discountDTO);
        return  ResponseEntity.ok().body(result);
    }

    @GetMapping("/discounts/{id}")
    public ResponseEntity<DiscountDTO> getDiscountById(@PathVariable Long id){
        Optional<DiscountDTO> discountDTO = discountService.getDiscourseById(id);
        if (discountDTO.isPresent()){
            return new ResponseEntity<>(discountDTO.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/discounts/code/{code}")
    public ResponseEntity<Discount> getDiscountByName(@PathVariable String code){
        Discount discountDTO = discountService.findName(code);
        return  ResponseEntity.ok().body(discountDTO);
    }

    @DeleteMapping("/discounts/{id}")
    @Transactional
    public void deleteDiscourse(@PathVariable long id){
        long existing = discountCourseRepository.findByDiscountId(id);
        this.discountCourseRepository.deleteAllDiscountId(existing);
        discountService.deleteDiscourse(id);
    }

    @GetMapping("/discounts/pages")
    public ResponseEntity<List<Discount>> getDiscountsPage(
            @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Discount> discountsPage = discountService.getDiscountPage(pageRequest);
        List<Discount> discounts = discountsPage.getContent();
        return ResponseEntity.ok().body(discounts);
    }

    @GetMapping("/discounts")
    public ResponseEntity<List<DiscountDTO>> getAllDiscount(){
        List<DiscountDTO> discounts = discountService.getAllDiscount();
        return ResponseEntity.ok().body(discounts);
    }
}
